package com.machiav3lli.derdiedas.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatDelegate
import com.machiav3lli.derdiedas.data.AppPrefs
import com.machiav3lli.derdiedas.data.Themes
import com.machiav3lli.derdiedas.utils.wrap
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

open class BaseActivity : ComponentActivity(), KoinComponent {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase.wrap())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setDayNightTheme(get<AppPrefs>().appTheme.value)
        super.onCreate(savedInstanceState)
    }

    fun setDayNightTheme(theme: String?) {
        when (theme) {
            Themes.LIGHT.value -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Themes.DARK.value -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}