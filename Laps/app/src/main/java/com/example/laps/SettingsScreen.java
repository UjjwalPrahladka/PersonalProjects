package com.example.laps;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingsScreen extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
