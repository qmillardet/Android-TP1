package com.example.projetamio;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.TextView;

public class Settings extends PreferenceActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

    }
}