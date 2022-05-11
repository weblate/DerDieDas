package com.machiav3lli.derdiedas.utils

import android.content.Context
import com.machiav3lli.derdiedas.PREFS_LANG
import com.machiav3lli.derdiedas.PREFS_LANG_SYSTEM
import com.machiav3lli.derdiedas.PREFS_SHARED
import com.machiav3lli.derdiedas.PREFS_THEME
import java.util.*

var Context.appTheme: String
    get() = getSharedPreferences(PREFS_SHARED, Context.MODE_PRIVATE)
        .getString(PREFS_THEME, "")
        ?: PREFS_LANG_SYSTEM
    set(value) = getSharedPreferences(PREFS_SHARED, Context.MODE_PRIVATE).edit()
        .putString(PREFS_THEME, value).apply()

var Context.language: String
    get() = getSharedPreferences(PREFS_SHARED, Context.MODE_PRIVATE)
        .getString(PREFS_LANG, PREFS_LANG_SYSTEM)
        ?: PREFS_LANG_SYSTEM
    set(value) = getSharedPreferences(PREFS_SHARED, Context.MODE_PRIVATE).edit()
        .putString(PREFS_LANG, value).apply()

fun Context.getLocaleOfCode(localeCode: String): Locale = when {
    localeCode.isEmpty() -> resources.configuration.locales[0]
    localeCode.contains("-r") -> Locale(
        localeCode.substring(0, 2),
        localeCode.substring(4)
    )
    localeCode.contains("_") -> Locale(
        localeCode.substring(0, 2),
        localeCode.substring(3)
    )
    else -> Locale(localeCode)
}

fun Locale.translate(): String {
    val country = getDisplayCountry(this)
    val language = getDisplayLanguage(this)
    return (language.replaceFirstChar { it.uppercase(Locale.getDefault()) }
            + (if (country.isNotEmpty() && country.compareTo(language, true) != 0)
        "($country)" else ""))
}
