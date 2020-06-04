package com.machiav3lli.derdiedas.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.machiav3lli.derdiedas.Constants;
import com.machiav3lli.derdiedas.R;
import com.machiav3lli.derdiedas.utils.PrefsUtil;

import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

public class SettingsFragment extends PreferenceFragmentCompat {
    final static String TAG = Constants.classTag(".PrefsFragment");

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        Preference pref;

        pref = findPreference(Constants.PREFS_THEME);
        assert pref != null;
        pref.setOnPreferenceChangeListener((preference, newValue) -> {
            PrefsUtil.setPrefsString(requireContext(), Constants.PREFS_THEME, newValue.toString());
            switch (newValue.toString()) {
                case "light":
                    setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                case "dark":
                    setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
                default:
                    setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            }
            return true;
        });
    }
}
