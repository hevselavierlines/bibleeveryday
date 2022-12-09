package tk.hevselavierlines.bibleeveryday.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tk.hevselavierlines.bibleeveryday.R;
import tk.hevselavierlines.bibleeveryday.helper.BibleArrayAdapter;
import tk.hevselavierlines.bibleeveryday.helper.Searcher;
import tk.hevselavierlines.bibleeveryday.model.Verse;


public class SearchFragment extends Fragment implements  View.OnClickListener, AdapterView.OnItemClickListener {
    private StringBuilder searchString;
    private TextView tvSearchInput;
    private ListView lvSearch;
    private List<Verse> searchItems;
    private Button btA, btB, btC, btD, btE, btF, btG, btH, btI, btJ, btK, btL, btM, btN, btO, btP,
            btQ, btR, btS, btT, btU, btV, btW, btX, btY, btZ, btBack, btBlank,
            bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9;
    private ArrayAdapter<Verse> adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        this.tvSearchInput = view.findViewById(R.id.tvSearchInput);

        searchString = new StringBuilder();
        this.btA = view.findViewById(R.id.buttonA);
        this.btA.setOnClickListener(this);
        this.btB = view.findViewById(R.id.buttonB);
        this.btB.setOnClickListener(this);
        this.btC = view.findViewById(R.id.buttonC);
        this.btC.setOnClickListener(this);
        this.btD = view.findViewById(R.id.buttonD);
        this.btD.setOnClickListener(this);
        this.btE = view.findViewById(R.id.buttonE);
        this.btE.setOnClickListener(this);
        this.btF = view.findViewById(R.id.buttonF);
        this.btF.setOnClickListener(this);
        this.btG = view.findViewById(R.id.buttonG);
        this.btG.setOnClickListener(this);
        this.btH = view.findViewById(R.id.buttonH);
        this.btH.setOnClickListener(this);
        this.btI = view.findViewById(R.id.buttonI);
        this.btI.setOnClickListener(this);
        this.btJ = view.findViewById(R.id.buttonJ);
        this.btJ.setOnClickListener(this);
        this.btK = view.findViewById(R.id.buttonK);
        this.btK.setOnClickListener(this);
        this.btL = view.findViewById(R.id.buttonL);
        this.btL.setOnClickListener(this);
        this.btM = view.findViewById(R.id.buttonM);
        this.btM.setOnClickListener(this);
        this.btN = view.findViewById(R.id.buttonN);
        this.btN.setOnClickListener(this);
        this.btO = view.findViewById(R.id.buttonO);
        this.btO.setOnClickListener(this);
        this.btP = view.findViewById(R.id.buttonP);
        this.btP.setOnClickListener(this);
        this.btQ = view.findViewById(R.id.buttonQ);
        this.btQ.setOnClickListener(this);
        this.btR = view.findViewById(R.id.buttonR);
        this.btR.setOnClickListener(this);
        this.btS = view.findViewById(R.id.buttonS);
        this.btS.setOnClickListener(this);
        this.btT = view.findViewById(R.id.buttonT);
        this.btT.setOnClickListener(this);
        this.btU = view.findViewById(R.id.buttonU);
        this.btU.setOnClickListener(this);
        this.btV = view.findViewById(R.id.buttonV);
        this.btV.setOnClickListener(this);
        this.btW = view.findViewById(R.id.buttonW);
        this.btW.setOnClickListener(this);
        this.btX = view.findViewById(R.id.buttonX);
        this.btX.setOnClickListener(this);
        this.btY = view.findViewById(R.id.buttonY);
        this.btY.setOnClickListener(this);
        this.btZ = view.findViewById(R.id.buttonZ);
        this.btZ.setOnClickListener(this);
        this.btBack = view.findViewById(R.id.buttonRet);
        this.btBack.setOnClickListener(this);
        this.btBlank = view.findViewById(R.id.buttonBlank);
        this.btBlank.setOnClickListener(this);
        this.bt0 = view.findViewById(R.id.button0);
        this.bt0.setOnClickListener(this);
        this.bt1 = view.findViewById(R.id.button1);
        this.bt1.setOnClickListener(this);
        this.bt2 = view.findViewById(R.id.button2);
        this.bt2.setOnClickListener(this);
        this.bt3 = view.findViewById(R.id.button3);
        this.bt3.setOnClickListener(this);
        this.bt4 = view.findViewById(R.id.button4);
        this.bt4.setOnClickListener(this);
        this.bt5 = view.findViewById(R.id.button5);
        this.bt5.setOnClickListener(this);
        this.bt6 = view.findViewById(R.id.button6);
        this.bt6.setOnClickListener(this);
        this.bt7 = view.findViewById(R.id.button7);
        this.bt7.setOnClickListener(this);
        this.bt8 = view.findViewById(R.id.button8);
        this.bt8.setOnClickListener(this);
        this.bt9 = view.findViewById(R.id.button9);
        this.bt9.setOnClickListener(this);

        lvSearch = view.findViewById(R.id.lvSearch);
        searchItems = new ArrayList<>();
        adapter = new BibleArrayAdapter(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, searchItems);
        lvSearch.setAdapter(adapter);
        lvSearch.setOnItemClickListener(this);
        this.updateSearchString();
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == btA) {
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
        getActivity().setResult(0x03, result);
        getActivity().finish();
    }
}