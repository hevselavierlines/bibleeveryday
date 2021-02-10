package tk.hevselavierlines.bibleeveryday;

import android.content.DialogInterface;
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
import tk.hevselavierlines.bibleeveryday.model.Verse;

public class BibleActivity extends AppCompatActivity implements View.OnClickListener, BibleObserver {

    private Toolbar toolbar;
    private ViewFlipper mViewFlipper;
    private ConstraintLayout clTextLayout;
    private TextView tvPage1;
    private TextView tvPage2;
    private int activeFlip;
    private FloatingActionButton fabNext;
    private FloatingActionButton fabPrev;
    private Button btNext;
    private Button btPrev;
    //previous
    private Animation outFromRight;
    private Animation inFromLeft;
    //next
    private Animation outFromLeft;
    private Animation inFromRight;

    private Bible bible;
    private Verse currentVerse;
    private int currentVerseAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bible);
        toolbar = findViewById(R.id.toolbar);

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

        tvPage1 = (TextView) findViewById(R.id.tvPage1);
        tvPage2 = (TextView) findViewById(R.id.tvPage2);

        tvPage1.setText("Loading...");
        tvPage1.setLineSpacing(0f, 1.0f);

        BibleLoader bibleLoader = new BibleLoader(this);
        try {
            bibleLoader.execute(getAssets().open("NIV.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor spe = this.getSharedPreferences("bible", MODE_PRIVATE).edit();
        spe.putInt("book", currentVerse.getChapter().getBook().getNumber());
        spe.putInt("chapter", currentVerse.getChapter().getNumber());
        spe.putInt("verse", currentVerse.getNumber());
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
        }
    }

    private void showSelectionDialog() {
//        AlertDialog.Builder adb = new AlertDialog.Builder(this);
//        LayoutInflater inflater = this.getLayoutInflater();
//        View versePicker = inflater.inflate(R.layout.verse_picker, null);
//        ListView lvBook = versePicker.findViewById(R.id.dialog_book);
//        ListView lvChapter = versePicker.findViewById(R.id.dialog_chapter);
//        ListView lvVerse = versePicker.findViewById(R.id.dialog_verse);
//        String[] bookItems = new String[bible.getBooks().size()];
//        int bookElement = 0;
//        for(Map.Entry<String, Book> entry : bible.getBooks().entrySet()) {
//            bookItems[bookElement++] = entry.getKey();
//        }
//        ArrayAdapter<String> adapterBooks = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_activated_1, android.R.id.text1, bookItems);
//
//        List<String> chapters = new ArrayList<String>();
//        chapters.add("1");
//        ArrayAdapter<String> adapterChapters = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_activated_1, android.R.id.text1, chapters);
//        lvChapter.setAdapter(adapterChapters);
//
//        List<String> verses = new ArrayList<String>();
//        ArrayAdapter<String> adapterVerses = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_activated_1, android.R.id.text1, verses);
//        lvVerse.setAdapter(adapterVerses);
//
//        lvBook.setAdapter(adapterBooks);
//        lvBook.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        lvBook.setItemChecked(0, true);
//        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                int index = 0;
//                String currbookselected = null;
//                for(String book : bible.getBooks().keySet()) {
//                    if(index == position) {
//                        currbookselected = book;
//                        break;
//                    }
//                    index++;
//                }
//                if(currbookselected != null) {
//                    chapters.clear();
//                    for(Integer chapter : bible.getBooks().get(currbookselected).getChapters().keySet()) {
//                        chapters.add(String.valueOf(chapter));
//                    }
//                    adapterChapters.notifyDataSetChanged();
//                }
//            }
//        });
//        adb.setView(versePicker);
//        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//        adb.show();
    }

    @Override
    public void bibleLoaded(Bible bible) {
        this.bible = bible;

        SharedPreferences biblePref = this.getSharedPreferences("bible", MODE_PRIVATE);
        currentVerseAmount = biblePref.getInt("verseAmount", 3);
        this.currentVerse = bible
                .getBooks().get(biblePref.getInt("book", 1))
                .getChapters().get(biblePref.getInt("chapter", 1))
                .getVerses().get(biblePref.getInt("verse", 1));

        Spanned verse = getBibleVerses(currentVerse, currentVerseAmount);
        tvPage1.setText(verse);
        //toolbar.setTitle(currentBook + " " + currentChapter + " " + currentVerse + "-" + (currentVerse + currentVerseAmount));
    }

    public Spanned getBibleVerses(Verse currentVerse, int verseAmount) {
        Chapter chapterObj = currentVerse.getChapter();
        int verseStart = currentVerse.getNumber();
        if (chapterObj != null) {
            if ((verseStart + verseAmount - 1) > chapterObj.countVerses()) {
                verseAmount = chapterObj.countVerses() - verseStart + 1;
            }
            String verses = chapterObj.getVersesRange(verseStart, verseAmount);
            toolbar.setTitle(chapterObj.getBook().getName() + " " + chapterObj.getNumber() + " " + currentVerse.getNumber() + "-" + (currentVerse.getNumber() + verseAmount - 1));
            return Html.fromHtml(verses, 0);
        } else {
            return Html.fromHtml("<h1>ERROR</h1>");
        }
    }

}