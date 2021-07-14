package com.machiav3lli.derdiedas.utils

import android.content.Context
import com.machiav3lli.derdiedas.PREFS_SHARED

object PrefsUtil {
    fun getPrefsString(context: Context, key: String?, def: String? = ""): String? =
        context.getSharedPreferences(PREFS_SHARED, Context.MODE_PRIVATE)
            .getString(key, def)

    fun setPrefsString(context: Context, key: String?, value: String?) =
        context.getSharedPreferences(PREFS_SHARED, Context.MODE_PRIVATE).edit()
            .putString(key, value).apply()
}