package tk.hevselavierlines.bibleeveryday;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import tk.hevselavierlines.bibleeveryday.model.Bible;
import tk.hevselavierlines.bibleeveryday.model.Book;
import tk.hevselavierlines.bibleeveryday.ui.main.PlaceholderFragment;
import tk.hevselavierlines.bibleeveryday.ui.main.SectionsPagerAdapter;

public class SelectionActivity extends AppCompatActivity {

    private Bible bible;
    private int selectionBook;
    private int selectionChapter;
    private int selectionVerse;
    private PlaceholderFragment[] fragments;
    private TabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        this.selectionBook = getIntent().getIntExtra("book", 1);
        this.selectionChapter = getIntent().getIntExtra("chapter", 1);
        this.selectionVerse = getIntent().getIntExtra("verse", 1);

        createFragments();
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), fragments);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        this.bible = Bible.getInstance();
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectionActivity.this.setResult(0x03);
                SelectionActivity.this.finish();
            }
        });
    }

    public int getSelectionBook() {
        return selectionBook;
    }

    public void setSelectionBook(int selectionBook) {
        this.selectionBook = selectionBook;
    }

    public int getSelectionChapter() {
        return selectionChapter;
    }

    public void setSelectionChapter(int selectionChapter) {
        this.selectionChapter = selectionChapter;
    }

    public int getSelectionVerse() {
        return selectionVerse;
    }

    public void setSelectionVerse(int selectionVerse) {
        this.selectionVerse = selectionVerse;
    }

    public void switchToChapter() {
        this.fragments[1].updateSelection(1, this.selectionBook);
        tabs.getTabAt(1).select();
    }

    public void switchToVerse() {
        this.fragments[2].updateSelection(2, this.selectionChapter);
        tabs.getTabAt(2).select();
    }

    private void createFragments() {
        this.fragments = new PlaceholderFragment[3];
        this.fragments[0] = new PlaceholderFragment(0);
        this.fragments[1] = new PlaceholderFragment(1);
        this.fragments[2] = new PlaceholderFragment(2);
    }

    public PlaceholderFragment getFragment(int index) {
        return fragments[index];
    }
}