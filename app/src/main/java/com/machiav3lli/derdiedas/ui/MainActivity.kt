package com.machiav3lli.derdiedas.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.preference.PreferenceManager
import com.machiav3lli.derdiedas.data.NounDatabase
import com.machiav3lli.derdiedas.data.WordViewModel
import com.machiav3lli.derdiedas.ui.theme.AppTheme
import com.machiav3lli.derdiedas.utils.appTheme
import com.machiav3lli.derdiedas.utils.createNounListFromAsset
import kotlinx.coroutines.runBlocking
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setDayNightTheme(appTheme)
        super.onCreate(savedInstanceState)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if (prefs.getBoolean("firstrun", true)) {
            NounDatabase.getInstance(this).let {
                runBlocking {
                    it.nounDao.deleteAll()
                    it.nounDao.insertAll(createNounListFromAsset())
                }
            }
            prefs.edit().putBoolean("firstrun", false).apply()
        }

        setContent {
            AppTheme {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                ) {
                    MainScreen(Modifier.padding(it))
                }
            }
        }
    }

    private fun setDayNightTheme(theme: String?) {
        when (theme) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}

val viewModelsModule = module {
    singleOf(::WordViewModel)
}