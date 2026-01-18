package com.machiav3lli.derdiedas.data

import android.content.Context
import androidx.annotation.StringRes
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.machiav3lli.derdiedas.PREFS_LANG_SYSTEM
import com.machiav3lli.derdiedas.R
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class AppPrefs private constructor(val context: Context) : KoinComponent {
    private val dataStore: DataStore<Preferences> by inject()

    val appTheme = PrefString(
        dataStore = dataStore,
        key = PrefKey.THEME,
        defaultValue = Themes.SYSTEM.value,
        entries = Themes.entries.toSet(),
    )

    val appLanguage = PrefString(
        dataStore = dataStore,
        key = PrefKey.LANGUAGE,
        defaultValue = PREFS_LANG_SYSTEM,
        entries = null,
    )

    val firstRun = PrefBoolean(
        dataStore = dataStore,
        key = PrefKey.FIRST_RUN,
        defaultValue = true,
    )

    companion object {
        val prefsModule = module {
            singleOf(::AppPrefs)
            singleOf(::provideDataStore)
        }

        private fun provideDataStore(context: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                produceFile = {
                    context.preferencesDataStoreFile("derdiedas_prefs")
                },
                migrations = listOf(
                    SharedPreferencesMigration(context, "${context.packageName}_preferences")
                ),
            )
        }
    }
}

enum class Themes(
    val value: String,
    @StringRes val labelRes: Int,
) {
    SYSTEM("system", R.string.theme_system),
    LIGHT("light", R.string.theme_light),
    DARK("dark", R.string.theme_dark);
}