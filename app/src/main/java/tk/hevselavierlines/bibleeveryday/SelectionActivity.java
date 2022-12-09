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
import tk.hevselavierlines.bibleeveryday.ui.main.SectionsPagerAdapter;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private int selectionBook;
    private int selectionChapter;
    private int selectionVerse;
    private PlaceholderFragment[] fragments;
    private TabLayout tabs;
    private Button searchButton;
    private ConstraintLayout searchView;
    private ViewPager selectView;
    private StringBuilder searchString;
    private TextView tvSearchInput;
    private ListView lvSearch;
    private List<Verse> searchItems;
    private Button btA, btB, btC, btD, btE, btF, btG, btH, btI, btJ, btK, btL, btM, btN, btO, btP,
        btQ, btR, btS, btT, btU, btV, btW, btX, btY, btZ, btBack, btBlank,
            bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9;
    private ArrayAdapter<Verse> adapter;

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
        this.searchView = findViewById(R.id.searchView);
        this.tvSearchInput = findViewById(R.id.tvSearchInput);

        this.selectionBook = getIntent().getIntExtra("book", 1);
        this.selectionChapter = getIntent().getIntExtra("chapter", 1);
        this.selectionVerse = getIntent().getIntExtra("verse", 1);

        searchString = new StringBuilder();
        this.btA = findViewById(R.id.buttonA);
        this.btA.setOnClickListener(this);
        this.btB = findViewById(R.id.buttonB);
        this.btB.setOnClickListener(this);
        this.btC = findViewById(R.id.buttonC);
        this.btC.setOnClickListener(this);
        this.btD = findViewById(R.id.buttonD);
        this.btD.setOnClickListener(this);
        this.btE = findViewById(R.id.buttonE);
        this.btE.setOnClickListener(this);
        this.btF = findViewById(R.id.buttonF);
        this.btF.setOnClickListener(this);
        this.btG = findViewById(R.id.buttonG);
        this.btG.setOnClickListener(this);
        this.btH = findViewById(R.id.buttonH);
        this.btH.setOnClickListener(this);
        this.btI = findViewById(R.id.buttonI);
        this.btI.setOnClickListener(this);
        this.btJ = findViewById(R.id.buttonJ);
        this.btJ.setOnClickListener(this);
        this.btK = findViewById(R.id.buttonK);
        this.btK.setOnClickListener(this);
        this.btL = findViewById(R.id.buttonL);
        this.btL.setOnClickListener(this);
        this.btM = findViewById(R.id.buttonM);
        this.btM.setOnClickListener(this);
        this.btN = findViewById(R.id.buttonN);
        this.btN.setOnClickListener(this);
        this.btO = findViewById(R.id.buttonO);
        this.btO.setOnClickListener(this);
        this.btP = findViewById(R.id.buttonP);
        this.btP.setOnClickListener(this);
        this.btQ = findViewById(R.id.buttonQ);
        this.btQ.setOnClickListener(this);
        this.btR = findViewById(R.id.buttonR);
        this.btR.setOnClickListener(this);
        this.btS = findViewById(R.id.buttonS);
        this.btS.setOnClickListener(this);
        this.btT = findViewById(R.id.buttonT);
        this.btT.setOnClickListener(this);
        this.btU = findViewById(R.id.buttonU);
        this.btU.setOnClickListener(this);
        this.btV = findViewById(R.id.buttonV);
        this.btV.setOnClickListener(this);
        this.btW = findViewById(R.id.buttonW);
        this.btW.setOnClickListener(this);
        this.btX = findViewById(R.id.buttonX);
        this.btX.setOnClickListener(this);
        this.btY = findViewById(R.id.buttonY);
        this.btY.setOnClickListener(this);
        this.btZ = findViewById(R.id.buttonZ);
        this.btZ.setOnClickListener(this);
        this.btBack = findViewById(R.id.buttonRet);
        this.btBack.setOnClickListener(this);
        this.btBlank = findViewById(R.id.buttonBlank);
        this.btBlank.setOnClickListener(this);
        this.bt0 = findViewById(R.id.button0);
        this.bt0.setOnClickListener(this);
        this.bt1 = findViewById(R.id.button1);
        this.bt1.setOnClickListener(this);
        this.bt2 = findViewById(R.id.button2);
        this.bt2.setOnClickListener(this);
        this.bt3 = findViewById(R.id.button3);
        this.bt3.setOnClickListener(this);
        this.bt4 = findViewById(R.id.button4);
        this.bt4.setOnClickListener(this);
        this.bt5 = findViewById(R.id.button5);
        this.bt5.setOnClickListener(this);
        this.bt6 = findViewById(R.id.button6);
        this.bt6.setOnClickListener(this);
        this.bt7 = findViewById(R.id.button7);
        this.bt7.setOnClickListener(this);
        this.bt8 = findViewById(R.id.button8);
        this.bt8.setOnClickListener(this);
        this.bt9 = findViewById(R.id.button9);
        this.bt9.setOnClickListener(this);

        createFragments();
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), fragments);
        selectView = findViewById(R.id.view_pager);
        selectView.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(selectView);

        lvSearch = findViewById(R.id.lvSearch);
        searchItems = new ArrayList<>();
//        Verse verse = new Verse();
//        verse.setNumber(1);
//        Chapter chapter = new Chapter();
//        chapter.setNumber(1);
//        Book book = new Book();
//        book.setName("Genesis");
//        chapter.setBook(book);
//        verse.setChapter(chapter);
//        searchItems.add(verse);

        adapter = new BibleArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, searchItems);
        lvSearch.setAdapter(adapter);
        lvSearch.setOnItemClickListener(this);
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
            this.selectView.setVisibility(View.INVISIBLE);
            this.searchView.setVisibility(View.VISIBLE);
            this.searchView.bringToFront();
            this.tabs.setVisibility(View.GONE);
            updateSearchString();
        } else if(view == btA) {
            searchString.append('A');
            updateSearchString();
        } else if(view == btB) {
            searchString.append('B');
            updateSearchString();
        } else if(view == btC) {
            searchString.append('C');
            updateSearchString();
        } else if(view == btD) {
            searchString.append('D');
            updateSearchString();
        } else if(view == btE) {
            searchString.append('E');
            updateSearchString();
        } else if(view == btF) {
            searchString.append('F');
            updateSearchString();
        } else if(view == btG) {
            searchString.append('G');
            updateSearchString();
        } else if(view == btH) {
            searchString.append('H');
            updateSearchString();
        } else if(view == btI) {
            searchString.append('I');
            updateSearchString();
        } else if(view == btJ) {
            searchString.append('J');
            updateSearchString();
        } else if(view == btK) {
            searchString.append('K');
            updateSearchString();
        } else if(view == btL) {
            searchString.append('L');
            updateSearchString();
        } else if(view == btM) {
            searchString.append('M');
            updateSearchString();
        } else if(view == btN) {
            searchString.append('N');
            updateSearchString();
        } else if(view == btO) {
            searchString.append('O');
            updateSearchString();
        } else if(view == btP) {
            searchString.append('P');
            updateSearchString();
        } else if(view == btQ) {
            searchString.append('Q');
            updateSearchString();
        } else if(view == btR) {
            searchString.append('R');
            updateSearchString();
        } else if(view == btS) {
            searchString.append('S');
            updateSearchString();
        } else if(view == btT) {
            searchString.append('T');
            updateSearchString();
        } else if(view == btU) {
            searchString.append('U');
            updateSearchString();
        } else if(view == btV) {
            searchString.append('V');
            updateSearchString();
        } else if(view == btW) {
            searchString.append('W');
            updateSearchString();
        } else if(view == btX) {
            searchString.append('X');
            updateSearchString();
        } else if(view == btY) {
            searchString.append('Y');
            updateSearchString();
        } else if(view == btZ) {
            searchString.append('Z');
            updateSearchString();
        } else if(view == btBack) {
            if(searchString.length() > 0) {
                searchString.deleteCharAt(searchString.length() - 1);
            }
            updateSearchString();
        } else if(view == btBlank) {
            searchString.append(' ');
            updateSearchString();
        } else if(view == bt0) {
            searchString.append('0');
            updateSearchString();
        } else if(view == bt1) {
            searchString.append('1');
            updateSearchString();
        } else if(view == bt2) {
            searchString.append('2');
            updateSearchString();
        } else if(view == bt3) {
            searchString.append('3');
            updateSearchString();
        } else if(view == bt4) {
            searchString.append('4');
            updateSearchString();
        } else if(view == bt5) {
            searchString.append('5');
            updateSearchString();
        } else if(view == bt6) {
            searchString.append('6');
            updateSearchString();
        } else if(view == bt7) {
            searchString.append('7');
            updateSearchString();
        } else if(view == bt8) {
            searchString.append('8');
            updateSearchString();
        } else if(view == bt9) {
            searchString.append('9');
            updateSearchString();
        }

    }

    public void updateSearchString() {
        tvSearchInput.setText(searchString.toString());
        List<Verse> foundItems = Searcher.getInstance().search(searchString.toString());
        this.searchItems.clear();
        this.searchItems.addAll(foundItems);
        this.adapter.notifyDataSetInvalidated();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Verse verse = searchItems.get(i);
        Intent result = new Intent();
        result.putExtra("book", verse.getChapter().getBook().getNumber());
        result.putExtra("chapter", verse.getChapter().getNumber());
        result.putExtra("verse", verse.getNumber() + 1);
        setResult(0x03, result);
        finish();
    }
}