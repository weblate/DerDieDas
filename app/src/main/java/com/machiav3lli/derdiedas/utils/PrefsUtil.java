package com.machiav3lli.derdiedas.utils;


import android.content.Context;

import com.machiav3lli.derdiedas.Constants;

public class PrefsUtil {

    public static String getPrefsString(Context context, String key, String def) {
        return context.getSharedPreferences(Constants.PREFS_SHARED, Context.MODE_PRIVATE).getString(key, def);
    }

    public static String getPrefsString(Context context, String key) {
        return getPrefsString(context, key, "");
    }

    public static void setPrefsString(Context context, String key, String value) {
        context.getSharedPreferences(Constants.PREFS_SHARED, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }
}
