package com.example.laps;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction().add(R.id.settingsView, new SettingsScreen()).commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent();
        setResult(RESULT_OK, intent2);
        finish();
    }
}
