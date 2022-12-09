package tk.hevselavierlines.bibleeveryday;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tk.hevselavierlines.bibleeveryday.helper.BibleArrayAdapter;
import tk.hevselavierlines.bibleeveryday.helper.Searcher;
import tk.hevselavierlines.bibleeveryday.model.Bible;
import tk.hevselavierlines.bibleeveryday.model.Book;
import tk.hevselavierlines.bibleeveryday.model.Chapter;
import tk.hevselavierlines.bibleeveryday.model.Storage;
import tk.hevselavierlines.bibleeveryday.model.Verse;
import tk.hevselavierlines.bibleeveryday.ui.main.PlaceholderFragment;
import tk.hevselavierlines.bibleeveryday.ui.main.SearchFragment;
import tk.hevselavierlines.bibleeveryday.ui.main.SectionsPagerAdapter;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private int selectionBook;
    private int selectionChapter;
    private int selectionVerse;
    private PlaceholderFragment[] fragments;
    private TabLayout tabs;
    private Button searchButton;
    private ViewPager selectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        Toolbar toolbar = findViewById(R.id.selectionToolbar);
        toolbar.setTitle("Select Bible Verse");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.searchButton = findViewById(R.id.selectionSearchButton);
        this.searchButton.setOnClickListener(this);

        this.selectionBook = getIntent().getIntExtra("book", 1);
        this.selectionChapter = getIntent().getIntExtra("chapter", 1);
        this.selectionVerse = getIntent().getIntExtra("verse", 1);



        createFragments();
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), fragments, new SearchFragment());
        selectView = findViewById(R.id.view_pager);
        selectView.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(selectView);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectionBook", selectionBook);
        outState.putInt("selectionChapter", selectionChapter);
        outState.putInt("selectionVerse", selectionVerse);

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
        this.setSelectionChapter(1);
        this.setSelectionVerse(1);
        tabs.getTabAt(1).select();
    }

    public void switchToVerse() {
        this.fragments[2].updateSelection(2, this.selectionChapter);
        this.setSelectionVerse(1);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view == searchButton) {
            tabs.getTabAt(3).select();
        }

    }

}