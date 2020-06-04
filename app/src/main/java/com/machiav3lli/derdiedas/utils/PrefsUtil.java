package com.machiav3lli.derdiedas.utils;


import android.content.Context;

import androidx.preference.PreferenceManager;

public class PrefsUtil {

    public static String getPrefsString(Context context, String key, String def) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, def);
    }

    public static String getPrefsString(Context context, String key) {
        return getPrefsString(context, key, "");
    }

    public static void setPrefsString(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply();
    }
}
