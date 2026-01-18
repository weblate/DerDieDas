package com.machiav3lli.derdiedas.utils

import android.content.Context
import java.util.Locale

fun Context.getLocaleOfCode(localeCode: String): Locale = when {
    localeCode.isEmpty() -> resources.configuration.locales[0]
    localeCode.contains("-r") -> Locale(
        localeCode.take(2),
        localeCode.substring(4)
    )

    localeCode.contains("_") -> Locale(
        localeCode.take(2),
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
