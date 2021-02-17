package tk.hevselavierlines.bibleeveryday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    private EditText etVerses;
    private Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etVerses = findViewById(R.id.etSettingsVerses);
        btSave = findViewById(R.id.btSettingsSave);

        int verses = getIntent().getIntExtra("verseAmount", 5);

        etVerses.setText(String.valueOf(verses));

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent result = new Intent();
                result.putExtra("verseAmount", Integer.parseInt(etVerses.getText().toString()));
                setResult(0x03, result);
                finish();
            }
        });
    }
}