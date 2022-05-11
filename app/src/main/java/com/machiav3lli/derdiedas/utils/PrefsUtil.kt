package com.machiav3lli.derdiedas.utils

import android.content.Context
import com.machiav3lli.derdiedas.PREFS_SHARED
import com.machiav3lli.derdiedas.PREFS_THEME

var Context.appTheme: String
    get() = getSharedPreferences(PREFS_SHARED, Context.MODE_PRIVATE)
        .getString(PREFS_THEME, "")
        ?: PREFS_LANG_SYSTEM
    set(value) = getSharedPreferences(PREFS_SHARED, Context.MODE_PRIVATE).edit()
        .putString(PREFS_THEME, value).apply()

