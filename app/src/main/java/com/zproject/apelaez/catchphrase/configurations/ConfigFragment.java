package com.zproject.apelaez.catchphrase.configurations;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.zproject.apelaez.catchphrase.R;

public class ConfigFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}