package tk.hevselavierlines.bibleeveryday.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tk.hevselavierlines.bibleeveryday.R;
import tk.hevselavierlines.bibleeveryday.SelectionActivity;
import tk.hevselavierlines.bibleeveryday.model.Bible;
import tk.hevselavierlines.bibleeveryday.model.Book;
import tk.hevselavierlines.bibleeveryday.model.Chapter;
import tk.hevselavierlines.bibleeveryday.model.Verse;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements AdapterView.OnItemClickListener {

    private int index;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> gridViewList;

    public List<String> getGridViewList() {
        return gridViewList;
    }

    public ArrayAdapter<String> getArrayAdapter() {
        return arrayAdapter;
    }

    public PlaceholderFragment(int index) {
        this.index = index;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void updateSelection(int viewIndex, int selectionIndex) {
        if(viewIndex == 1) {
            gridViewList.clear();
            Book book = Bible.getInstance().getBooks().get(selectionIndex);
            for(Map.Entry<Integer, Chapter> chapter : book.getChapters().entrySet()) {
                gridViewList.add(String.valueOf(chapter.getKey()));
            }
            arrayAdapter.notifyDataSetChanged();
        } else if(viewIndex == 2) {
            SelectionActivity selectionActivity = (SelectionActivity) getActivity();
            PlaceholderFragment fragment = selectionActivity.getFragment(2);
            fragment.getGridViewList().clear();
            Chapter chapter = Bible.getInstance().getBooks().get(selectionActivity.getSelectionBook()).getChapters().get(selectionIndex);
            for(Map.Entry<Integer, Verse> verse : chapter.getVerses().entrySet()) {
                fragment.getGridViewList().add(String.valueOf(verse.getKey()));
            }
            fragment.getArrayAdapter().notifyDataSetChanged();
            arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_selection, container, false);
        GridView gridView = root.findViewById(R.id.selectionGrid);
        gridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);

        SelectionActivity selectionActivity = (SelectionActivity) getActivity();
        gridViewList = new ArrayList<>();
        if(index == 0) {
            for (Map.Entry<Integer, Book> book : Bible.getInstance().getBooks().entrySet()) {
                gridViewList.add(book.getValue().getName());
            }
        } else if(index == 1) {
            for (Map.Entry<Integer, Chapter> chapter : Bible.getInstance().getBooks().get(selectionActivity.getSelectionBook()).getChapters().entrySet()) {
                gridViewList.add(String.valueOf(chapter.getKey()));
            }
        } else if(index == 2) {
            for (Map.Entry<Integer, Verse> verse : Bible.getInstance().getBooks().get(selectionActivity.getSelectionBook()).getChapters().get(selectionActivity.getSelectionChapter()).getVerses().entrySet()) {
                gridViewList.add(String.valueOf(verse.getKey()));
            }
        }

        arrayAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.biblelist, android.R.id.text1, gridViewList);
        gridView.setAdapter(arrayAdapter);

        if(index == 0) {
            gridView.setItemChecked(selectionActivity.getSelectionBook() - 1, true);
        } else if(index == 1) {
            gridView.setItemChecked(selectionActivity.getSelectionChapter() - 1, true);
        } else if(index == 2) {
            gridView.setItemChecked(selectionActivity.getSelectionVerse() - 1, true);
        }
        gridView.setOnItemClickListener(this);
        return root;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bible bible = Bible.getInstance();
        SelectionActivity selectionActivity = (SelectionActivity) getActivity();
        if(index == 0) {
            Book book = bible.getBooks().get(position + 1);

            selectionActivity.setSelectionBook(position + 1);
            selectionActivity.switchToChapter();
        } else if(index == 1) {
            Chapter chapter = bible.getBooks().get(selectionActivity.getSelectionBook()).getChapters().get(position + 1);

            selectionActivity.setSelectionChapter(position + 1);
            selectionActivity.switchToVerse();
        } else if(index == 2) {
            Intent result = new Intent();
            result.putExtra("book", selectionActivity.getSelectionBook());
            result.putExtra("chapter", selectionActivity.getSelectionChapter());
            result.putExtra("verse", position + 1);
            selectionActivity.setResult(0x03, result);
            selectionActivity.finish();
        }
    }
}