package com.example.greddy.weatherapp.ui.settings;

import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.greddy.weatherapp.R;

/**
 * Created by greddy on 5/24/2017.
 */

public class SettingsPreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_preferences);
        ListPreference listPreference = (ListPreference) findPreference(getString(R.string.settings_temp_unit));
        if(listPreference.getValue() == null){
            listPreference.setValueIndex(0); //set to index of your deafult value
        }
    }
}
