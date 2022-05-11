package com.machiav3lli.derdiedas.utils

import android.content.Context
import com.machiav3lli.derdiedas.PREFS_SHARED

fun Context.getPrefsString(key: String?) =
    getSharedPreferences(PREFS_SHARED, Context.MODE_PRIVATE)
        .getString(key, "")

fun Context.setPrefsString(key: String?, value: String?) =
    getSharedPreferences(PREFS_SHARED, Context.MODE_PRIVATE)
        .edit()
        .putString(key, value).apply()
