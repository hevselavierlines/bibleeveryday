package tk.hevselavierlines.bibleeveryday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spanned;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import tk.hevselavierlines.bibleeveryday.loader.BibleLoader;
import tk.hevselavierlines.bibleeveryday.loader.BibleObserver;
import tk.hevselavierlines.bibleeveryday.model.Bible;
import tk.hevselavierlines.bibleeveryday.model.Storage;

public class StartupActivity extends AppCompatActivity implements BibleObserver {

    private BibleLoader bibleLoader;
    private String defaultFile = "ENG_New_International_Version";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        this.bibleLoader = new BibleLoader(this);
        SharedPreferences sharedPreferences = this.getSharedPreferences("bible", MODE_PRIVATE);
        String version = sharedPreferences.getString("version", "ENG_New_International_Version") + ".xml";

        loadBible(version);
    }

    private void loadBible(String xmlFile) {
        try {
            bibleLoader.execute(getAssets().open(xmlFile));
        } catch (IOException e) {
            e.printStackTrace();
            try {
                bibleLoader.execute(getAssets().open(defaultFile + ".xml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
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