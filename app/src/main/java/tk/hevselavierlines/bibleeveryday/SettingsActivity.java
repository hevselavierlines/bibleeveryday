package tk.hevselavierlines.bibleeveryday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class SettingsActivity extends AppCompatActivity {

    private EditText etVerses;
    private RadioGroup rgVersion;
    //private Spinner spBibleVersion;
    private final String[] BIBLE_VERSIONS = new String[] {"NIV", "schlachter", "volxbible"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etVerses = findViewById(R.id.etSettingsVerses);
        //spBibleVersion = findViewById(R.id.spBibleVersion);
        rgVersion = findViewById(R.id.rgVersion);



        Button btSave = findViewById(R.id.btSettingsSave);
        Button btCancel = findViewById(R.id.btSettingsCancel);
        SeekBar sbTextSize = findViewById(R.id.sbTextSize);

        int textSize = getIntent().getIntExtra("textSize", 25);
        String currentVersion = getIntent().getStringExtra("version");

        sbTextSize.setProgress(textSize);
        etVerses.setText(String.valueOf(textSize));

        int i = 0;
        for(String bibleVersion : BIBLE_VERSIONS) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(bibleVersion);
            radioButton.setId(i++);//set radiobutton id and store it somewhere
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            if(bibleVersion.equals(currentVersion)) {
                radioButton.setChecked(true);
            }
            rgVersion.addView(radioButton, params);
        }
        /*
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
        }*/

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

        int id = rgVersion.getCheckedRadioButtonId();
        if(id < 0 || id >= BIBLE_VERSIONS.length) {
            id = 0;
        }
        result.putExtra("version", BIBLE_VERSIONS[id]);
        setResult(0x03, result);
        finish();
    }
}