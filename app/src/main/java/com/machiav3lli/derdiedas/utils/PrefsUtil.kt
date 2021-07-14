package com.machiav3lli.derdiedas.utils

import android.content.Context
import com.machiav3lli.derdiedas.Constants

object PrefsUtil {
    fun getPrefsString(context: Context, key: String?, def: String? = ""): String? =
        context.getSharedPreferences(Constants.PREFS_SHARED, Context.MODE_PRIVATE)
            .getString(key, def)

    fun setPrefsString(context: Context, key: String?, value: String?) =
        context.getSharedPreferences(Constants.PREFS_SHARED, Context.MODE_PRIVATE).edit()
            .putString(key, value).apply()
}