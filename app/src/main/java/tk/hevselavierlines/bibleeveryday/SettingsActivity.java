package tk.hevselavierlines.bibleeveryday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class SettingsActivity extends AppCompatActivity {

    private EditText etVerses;
    private Spinner spBibleVersion;
    private final String[] BIBLE_VERSIONS = new String[] {"NIV", "schlachter", "volxbible"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etVerses = findViewById(R.id.etSettingsVerses);
        spBibleVersion = findViewById(R.id.spBibleVersion);
        Button btSave = findViewById(R.id.btSettingsSave);
        Button btCancel = findViewById(R.id.btSettingsCancel);
        SeekBar sbTextSize = findViewById(R.id.sbTextSize);

        int textSize = getIntent().getIntExtra("textSize", 25);
        String currentVersion = getIntent().getStringExtra("version");

        sbTextSize.setProgress(textSize);
        etVerses.setText(String.valueOf(textSize));

        SpinnerAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, BIBLE_VERSIONS);
        spBibleVersion.setAdapter(adapter);

        int selPos = -1;
        if(currentVersion != null) {
            for(int i = 0; i < BIBLE_VERSIONS.length && selPos < 0; i++) {
                if(currentVersion.equals(BIBLE_VERSIONS[i])) {
                    selPos = i;
                }
            }
            if(selPos >= 0) {
                spBibleVersion.setSelection(selPos, true);
            }
        }

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResultToReturn();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sbTextSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                etVerses.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        sendResultToReturn();
        return super.onOptionsItemSelected(item);
    }

    private void sendResultToReturn() {
        Intent result = new Intent();
        result.putExtra("textSize", Integer.parseInt(etVerses.getText().toString()));
        result.putExtra("version", spBibleVersion.getSelectedItem().toString());
        setResult(0x03, result);
        finish();
    }
}