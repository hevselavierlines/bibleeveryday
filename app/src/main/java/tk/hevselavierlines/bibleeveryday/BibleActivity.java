package tk.hevselavierlines.bibleeveryday;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import tk.hevselavierlines.bibleeveryday.model.Bible;
import tk.hevselavierlines.bibleeveryday.model.Book;
import tk.hevselavierlines.bibleeveryday.model.Chapter;
import tk.hevselavierlines.bibleeveryday.model.Storage;
import tk.hevselavierlines.bibleeveryday.ui.main.PageSwipeTouchListener;
import tk.hevselavierlines.bibleeveryday.ui.main.PaginationController;

public class BibleActivity extends AppCompatActivity implements View.OnClickListener, PaginationController.PageUpdateListener {

    private Toolbar toolbar;
    private ViewFlipper mViewFlipper;
    private TextView tvPage1;
    private TextView tvPage2;
    private TextView tvToolbar;
    private Button toolbarPrev;
    private Button toolbarNext;
    private Button btToolbarSettings;
    //previous
    private Animation outFromRight;
    private Animation inFromLeft;
    //next
    private Animation outFromLeft;
    private Animation inFromRight;

    private Chapter currentChapter;
    private int currentVerse;

    private PaginationController paginationController;
    private int textSize;
    private String currentVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_bible);
            toolbar = findViewById(R.id.toolbar);
            tvToolbar = findViewById(R.id.toolbar_title);
            btToolbarSettings = findViewById(R.id.toolbar_settings);

            toolbarPrev = findViewById(R.id.bible_back);
            toolbarNext = findViewById(R.id.bible_forward);

            toolbar.setOnClickListener(this);
            setSupportActionBar(toolbar);

            mViewFlipper = (ViewFlipper) this.findViewById(R.id.vfText);

            outFromLeft = AnimationUtils.loadAnimation(this, R.anim.out_from_left);
            inFromRight = AnimationUtils.loadAnimation(this, R.anim.in_from_right);

            outFromRight = AnimationUtils.loadAnimation(this, R.anim.out_from_right);
            inFromLeft = AnimationUtils.loadAnimation(this, R.anim.in_from_left);

            toolbarPrev.setOnClickListener(this);
            toolbarNext.setOnClickListener(this);
            btToolbarSettings.setOnClickListener(this);
            tvToolbar.setOnClickListener(this);


            tvPage1 = (TextView) findViewById(R.id.tvPage1);
            tvPage2 = (TextView) findViewById(R.id.tvPage2);

            paginationController = new PaginationController(tvPage1, tvPage2, this);
            PageSwipeTouchListener pageSwipeTouchListener = new PageSwipeTouchListener(new PageSwipeTouchListener.PageChangeListener() {
                @Override
                public void previousPage() {
                    gotoPreviousPage();
                }

                @Override
                public void nextPage() {
                    gotoNextPage();
                }

                @Override
                public void showMenu() {
                    showSelectionDialog();
                }
            }, tvPage1, tvPage2);

            if (Storage.getInstance().getBible() != null) {
                this.loadCurrentVerse();
            } else {
                Intent reload = new Intent(BibleActivity.this, StartupActivity.class);
                startActivity(reload);
                finish();
            }
        } catch (RuntimeException ex) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setMessage(ex.getMessage());
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            adb.show();
        }
    }

    void firstSetText(Chapter chapter) {
        Spanned text = Html.fromHtml(chapter.toString(), 0);
        paginationController.onTextLoaded(text, true);
    }

    void updateText(Chapter chapter) {
        Spanned text = Html.fromHtml(chapter.toString(), 0);
        paginationController.onTextLoaded(text, false);
    }

    private void loadCurrentVerse() {
        SharedPreferences biblePref = this.getSharedPreferences("bible", MODE_PRIVATE);
        currentVersion = biblePref.getString("version", "NIV");
        textSize = biblePref.getInt("textSize", 25);
        setTextSize();
        Bible bible = Storage.getInstance().getBible();
        Book book = bible.getBooks().get(biblePref.getInt("book", 1));
        if(book == null) {
            book = bible.getBooks().get(1);
        }
        Chapter chapter = book.getChapters().get(biblePref.getInt("chapter", 1));
        if(chapter == null) {
            chapter = book.getChapters().get(1);
        }
        this.currentChapter = chapter;
        firstSetText(chapter);
        int verse = biblePref.getInt("verse", 1);
        paginationController.openVerse(verse);
    }

    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        storeCurrentProperties();
    }

    protected void storeCurrentProperties() {
        SharedPreferences.Editor spe = this.getSharedPreferences("bible", MODE_PRIVATE).edit();
        spe.putInt("book", currentChapter.getBook().getNumber());
        spe.putInt("chapter", currentChapter.getNumber());
        spe.putInt("verse", currentVerse);
        spe.putInt("textSize", textSize);
        spe.putString("version", currentVersion);
        spe.apply();
    }

    protected void gotoNextPage() {
        mViewFlipper.setOutAnimation(outFromLeft);
        mViewFlipper.setInAnimation(inFromRight);

        if(paginationController.isNextEnabled()) {
            paginationController.next();
            mViewFlipper.showNext();
        } else {
            Chapter nextChapter = Chapter.getNextChapter(currentChapter);
            if(nextChapter != null) {
                currentChapter = nextChapter;
                updateText(currentChapter);
                paginationController.openFirstPage();
                mViewFlipper.showNext();
            }
        }
    }

    protected void gotoPreviousPage() {
        mViewFlipper.setOutAnimation(outFromRight);
        mViewFlipper.setInAnimation(inFromLeft);

        if(paginationController.isPreviousEnabled()) {
            paginationController.previous();
            mViewFlipper.showPrevious();
        } else {
            Chapter prevChapter = Chapter.getPreviousChapter(currentChapter);
            if(prevChapter != null) {
                currentChapter = prevChapter;
                this.currentVerse = currentChapter.countVerses();
                updateText(currentChapter);
                paginationController.openLastPage();
                mViewFlipper.showPrevious();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == toolbarPrev || v == toolbar) {
            gotoPreviousPage();
        } else if (v == toolbarNext) {
            gotoNextPage();
        } else if (v == tvToolbar) {
            this.showSelectionDialog();
        } else if (v == btToolbarSettings) {
            Intent settingsIntent = new Intent(BibleActivity.this, SettingsActivity.class);
            settingsIntent.putExtra("version", this.currentVersion);
            settingsIntent.putExtra("textSize", this.textSize);
            startActivityForResult(settingsIntent, 0x02);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x03 && data != null) {
            int book = data.getIntExtra("book", this.currentChapter.getBook().getNumber());
            int chapter = data.getIntExtra("chapter", this.currentChapter.getNumber());
            int verse = data.getIntExtra("verse", 1);

            Book currentBook = Storage.getInstance().getBible().getBooks().get(book);
            if(currentBook == null) {
                currentBook = Storage.getInstance().getBible().getBooks().get(1);
            }
            Chapter currentChapter = currentBook.getChapters().get(chapter);
            if(currentChapter == null) {
                currentChapter = currentBook.getChapters().get(1);
            }
            this.currentChapter = currentChapter;
            updateText(this.currentChapter);
            paginationController.openVerse(verse);
        } else if(requestCode == 0x02 && data != null) {
            String newVersion = data.getStringExtra("version");
            int newTextSize = data.getIntExtra("textSize", this.textSize);
            if(newVersion == null) {
                newVersion = this.currentVersion;
            }
            if(!newVersion.equals(this.currentVersion)) {
                this.currentVersion = newVersion;
                storeCurrentProperties();
                Intent reload = new Intent(BibleActivity.this, StartupActivity.class);
                startActivity(reload);
                finish();
            }
            if(newTextSize != this.textSize) {
                this.textSize = data.getIntExtra("textSize", this.textSize);
                setTextSize();
                updateText(this.currentChapter);
                paginationController.openVerse(currentVerse);
            }

        }
    }

    private void showSelectionDialog() {
        Intent in = new Intent(BibleActivity.this, SelectionActivity.class);
        in.putExtra("book", this.currentChapter.getBook().getNumber());
        in.putExtra("chapter", this.currentChapter.getNumber());
        in.putExtra("verse", currentVerse);
        in.putExtra("version", this.currentVersion);
        startActivityForResult(in, 0x03);
    }

    @Override
    public void updatePage(int startVerse, int endVerse) {
        if(startVerse == 0) {
            startVerse = this.currentVerse;
            endVerse = this.currentVerse;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(currentChapter.getBook().getName());
        sb.append(' ');
        sb.append(currentChapter.getNumber());
        sb.append(' ');
        sb.append(startVerse);
        if(startVerse != endVerse) {
            sb.append('-');
            sb.append(endVerse);
        }
        tvToolbar.setText(sb.toString());
        this.currentVerse = endVerse;
    }

    public void setTextSize() {
        tvPage1.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tvPage2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }
}