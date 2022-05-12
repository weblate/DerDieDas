package com.machiav3lli.derdiedas.utils

import android.content.ComponentName
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Configuration
import com.machiav3lli.derdiedas.PREFS_LANG_SYSTEM
import com.machiav3lli.derdiedas.ui.MainActivity
import java.util.*

fun Context.restartApp() = startActivity(
    Intent.makeRestartActivityTask(
        ComponentName(this, MainActivity::class.java)
    )
)

fun Context.wrap(): ContextWrapper {
    val config = setLanguage()
    return ContextWrapper(createConfigurationContext(config))
}

fun Context.setLanguage(): Configuration {
    var setLocalCode = language
    if (setLocalCode == PREFS_LANG_SYSTEM) {
        setLocalCode = Locale.getDefault().toString()
    }
    val config = resources.configuration
    val sysLocale = config.locales[0]
    if (setLocalCode != sysLocale.language || setLocalCode != "${sysLocale.language}-r${sysLocale.country}") {
        val newLocale = getLocaleOfCode(setLocalCode)
        Locale.setDefault(newLocale)
        config.setLocale(newLocale)
    }
    return config
}
