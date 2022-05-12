package com.machiav3lli.derdiedas.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.machiav3lli.derdiedas.PREFS_LANG
import com.machiav3lli.derdiedas.PREFS_THEME
import com.machiav3lli.derdiedas.R
import com.machiav3lli.derdiedas.utils.appTheme
import com.machiav3lli.derdiedas.utils.language
import com.machiav3lli.derdiedas.utils.restartApp

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        findPreference<Preference>(PREFS_THEME)?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _: Preference?, newValue: Any ->
                requireContext().appTheme = newValue.toString()
                when (newValue.toString()) {
                    "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                true
            }

        findPreference<ListPreference>(PREFS_LANG)?.apply {
            val oldLang = value
            onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _: Preference?, newValue: Any ->
                    val changed = oldLang != newValue.toString()
                    if (changed) {
                        requireContext().language = newValue.toString()
                        requireContext().restartApp()
                    }
                    changed
                }
        }
    }
}