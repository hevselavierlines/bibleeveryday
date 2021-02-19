package tk.hevselavierlines.bibleeveryday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spanned;

import java.io.IOException;

import tk.hevselavierlines.bibleeveryday.loader.BibleLoader;
import tk.hevselavierlines.bibleeveryday.loader.BibleObserver;
import tk.hevselavierlines.bibleeveryday.model.Bible;
import tk.hevselavierlines.bibleeveryday.model.Storage;

public class StartupActivity extends AppCompatActivity implements BibleObserver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        BibleLoader bibleLoader = new BibleLoader(this);
        try {
            bibleLoader.execute(getAssets().open("volxbible.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void bibleLoaded(Bible bible) {
        Storage.getInstance().setBible(bible);

        Intent intent = new Intent(this, BibleActivity.class);
        startActivity(intent);
        finish();
    }
}