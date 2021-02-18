package tk.hevselavierlines.bibleeveryday;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.Html;
import android.text.Layout;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tk.hevselavierlines.bibleeveryday.loader.BibleLoader;
import tk.hevselavierlines.bibleeveryday.loader.BibleObserver;
import tk.hevselavierlines.bibleeveryday.loader.MapXmlToBible;
import tk.hevselavierlines.bibleeveryday.model.Bible;
import tk.hevselavierlines.bibleeveryday.model.Book;
import tk.hevselavierlines.bibleeveryday.model.Chapter;
import tk.hevselavierlines.bibleeveryday.model.Storage;
import tk.hevselavierlines.bibleeveryday.model.Verse;

public class BibleActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ViewFlipper mViewFlipper;
    private ConstraintLayout clTextLayout;
    private TextView tvPage1;
    private TextView tvPage2;
    private TextView tvToolbar;
    private int activeFlip;
    private FloatingActionButton fabNext;
    private FloatingActionButton fabPrev;
    private Button btNext;
    private Button btPrev;
    private Button btToolbarSettings;
    //previous
    private Animation outFromRight;
    private Animation inFromLeft;
    //next
    private Animation outFromLeft;
    private Animation inFromRight;

    private Verse currentVerse;
    private int currentVerseAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bible);
        toolbar = findViewById(R.id.toolbar);
        tvToolbar = findViewById(R.id.toolbar_title);
        btToolbarSettings = findViewById(R.id.toolbar_settings);

        activeFlip = 1;
        toolbar.setOnClickListener(this);
        setSupportActionBar(toolbar);

        clTextLayout = (ConstraintLayout) findViewById(R.id.textLayout);
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.vfText);

        outFromLeft = AnimationUtils.loadAnimation(this, R.anim.out_from_left);
        inFromRight = AnimationUtils.loadAnimation(this, R.anim.in_from_right);

        outFromRight = AnimationUtils.loadAnimation(this, R.anim.out_from_right);
        inFromLeft = AnimationUtils.loadAnimation(this, R.anim.in_from_left);

        fabNext = findViewById(R.id.fabNext);
        fabPrev = findViewById(R.id.fabPrev);
        btPrev = findViewById(R.id.btPrev);
        btNext = findViewById(R.id.btNext);
        fabNext.setOnClickListener(this);
        fabPrev.setOnClickListener(this);
        btPrev.setOnClickListener(this);
        btNext.setOnClickListener(this);
        btToolbarSettings.setOnClickListener(this);

        tvPage1 = (TextView) findViewById(R.id.tvPage1);
        tvPage2 = (TextView) findViewById(R.id.tvPage2);


        tvPage1.setLineSpacing(0f, 1.0f);
        SharedPreferences biblePref = this.getSharedPreferences("bible", MODE_PRIVATE);
        currentVerseAmount = biblePref.getInt("verseAmount", 5);
        this.currentVerse = Storage.getInstance().getBible()
                .getBooks().get(biblePref.getInt("book", 1))
                .getChapters().get(biblePref.getInt("chapter", 1))
                .getVerses().get(biblePref.getInt("verse", 1));

        Spanned verse = getBibleVerses(currentVerse, currentVerseAmount);
        tvPage1.setText(verse);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor spe = this.getSharedPreferences("bible", MODE_PRIVATE).edit();
        spe.putInt("book", currentVerse.getChapter().getBook().getNumber());
        spe.putInt("chapter", currentVerse.getChapter().getNumber());
        spe.putInt("verse", currentVerse.getNumber());
        spe.putInt("verseAmount", currentVerseAmount);
        spe.commit();
    }

    @Override
    public void onClick(View v) {
        if (v == fabPrev || v == btPrev) {
            if (currentVerse != null) {
                mViewFlipper.setOutAnimation(outFromRight);
                mViewFlipper.setInAnimation(inFromLeft);

                currentVerse = currentVerse.getPreviousVerse(currentVerseAmount);
                Spanned bibleText = getBibleVerses(currentVerse, currentVerseAmount);
                if (activeFlip == 1) {
                    tvPage2.setText(bibleText);
                    activeFlip = 2;
                } else {
                    tvPage1.setText(bibleText);
                    activeFlip = 1;
                }

                mViewFlipper.showPrevious();
            }
        } else if (v == fabNext || v == btNext) {
            if (currentVerse != null) {
                mViewFlipper.setOutAnimation(outFromLeft);
                mViewFlipper.setInAnimation(inFromRight);
                currentVerse = currentVerse.getNextVerse(currentVerseAmount);

                Spanned bibleText = getBibleVerses(currentVerse, currentVerseAmount);
                if (activeFlip == 1) {
                    tvPage2.setText(bibleText);
                    activeFlip = 2;
                } else {
                    tvPage1.setText(bibleText);
                    activeFlip = 1;
                }
                mViewFlipper.showNext();
            }
        } else if (v == toolbar) {
            this.showSelectionDialog();
        } else if (v == btToolbarSettings) {
            Intent settingsIntent = new Intent(BibleActivity.this, SettingsActivity.class);
            settingsIntent.putExtra("verseAmount", this.currentVerseAmount);
            startActivityForResult(settingsIntent, 0x02);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == 0x03 && data != null) {
            int book = data.getIntExtra("book", this.currentVerse.getChapter().getBook().getNumber());
            int chapter = data.getIntExtra("chapter", this.currentVerse.getChapter().getNumber());
            int verse = data.getIntExtra("verse", this.currentVerse.getNumber());

            Book currentBook = Storage.getInstance().getBible().getBooks().get(book);
            if(currentBook == null) {
                currentBook = Storage.getInstance().getBible().getBooks().get(1);
            }
            Chapter currentChapter = currentBook.getChapters().get(chapter);
            if(currentChapter == null) {
                currentChapter = currentBook.getChapters().get(1);
            }
            Verse currVerse = currentChapter.getVerses().get(verse);
            if(currVerse == null) {
                currVerse = currentChapter.getVerses().get(1);
            }

            this.currentVerse = currVerse;
            Spanned bibleText = getBibleVerses(currentVerse, currentVerseAmount);
            if (activeFlip == 1) {
                tvPage1.setText(bibleText);
            } else {
                tvPage2.setText(bibleText);
            }
        } else if(requestCode == 0x02 && data != null) {
            this.currentVerseAmount = data.getIntExtra("verseAmount", this.currentVerseAmount);
            Spanned bibleText = getBibleVerses(currentVerse, currentVerseAmount);
            if (activeFlip == 1) {
                tvPage1.setText(bibleText);
            } else {
                tvPage2.setText(bibleText);
            }
        }
    }

    private void showSelectionDialog() {
        Intent in = new Intent(BibleActivity.this, SelectionActivity.class);
        in.putExtra("book", this.currentVerse.getChapter().getBook().getNumber());
        in.putExtra("chapter", this.currentVerse.getChapter().getNumber());
        in.putExtra("verse", this.currentVerse.getNumber());
        startActivityForResult(in, 0x03);
    }



    public Spanned getBibleVerses(Verse currentVerse, int verseAmount) {
        Chapter chapterObj = currentVerse.getChapter();
        int verseStart = currentVerse.getNumber();
        if (chapterObj != null) {
            if ((verseStart + verseAmount - 1) > chapterObj.countVerses()) {
                verseAmount = chapterObj.countVerses() - verseStart + 1;
            }
            String verses = chapterObj.getVersesRange(verseStart, verseAmount);
            tvToolbar.setText(chapterObj.getBook().getName() + " " + chapterObj.getNumber() + " " + currentVerse.getNumber() + "-" + (currentVerse.getNumber() + verseAmount - 1));
            toolbar.setTitle(chapterObj.getBook().getName() + " " + chapterObj.getNumber() + " " + currentVerse.getNumber() + "-" + (currentVerse.getNumber() + verseAmount - 1));
            return Html.fromHtml(verses, 0);
        } else {
            return Html.fromHtml("<h1>ERROR</h1>");
        }
    }

}