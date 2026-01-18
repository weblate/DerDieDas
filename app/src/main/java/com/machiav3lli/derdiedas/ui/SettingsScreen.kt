package com.machiav3lli.derdiedas.ui

import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.machiav3lli.derdiedas.R
import com.machiav3lli.derdiedas.data.AppPrefs
import com.machiav3lli.derdiedas.data.Themes
import com.machiav3lli.derdiedas.locales.DetectedLocales
import com.machiav3lli.derdiedas.ui.icons.Phosphor
import com.machiav3lli.derdiedas.ui.icons.phosphor.ArrowCircleLeft
import com.machiav3lli.derdiedas.utils.getLocaleOfCode
import com.machiav3lli.derdiedas.utils.restartApp
import com.machiav3lli.derdiedas.utils.translate
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    prefs: AppPrefs = koinInject(),
    onBackPressed: () -> Unit,
) {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val scope = rememberCoroutineScope()
    val currentTheme by prefs.appTheme.flow.collectAsState(initial = Themes.SYSTEM.value)
    val currentLanguage by prefs.appLanguage.flow.collectAsState(initial = "system")

    var showThemeDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    RoundButton(
                        icon = Phosphor.ArrowCircleLeft,
                        description = stringResource(R.string.dialog_back),
                    ) {
                        onBackPressed()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PreferenceItem(
                title = stringResource(R.string.prefs_theme),
                summary = getThemeLabel(currentTheme),
                onClick = { showThemeDialog = true }
            )
            PreferenceItem(
                title = stringResource(R.string.prefs_language),
                summary = getLanguageLabel(currentLanguage),
                onClick = { showLanguageDialog = true }
            )
        }

        if (showThemeDialog) {
            val themeEntries = Themes.entries.map { stringResource(it.labelRes) }.toTypedArray()
            val themeValues = Themes.entries.map { it.value }.toTypedArray()

            ListPreferenceDialog(
                title = stringResource(R.string.prefs_theme),
                entries = themeEntries,
                entryValues = themeValues,
                selectedValue = currentTheme,
                onDismiss = { showThemeDialog = false },
                onValueSelected = { newValue ->
                    scope.launch {
                        prefs.appTheme.set(newValue)
                        when (newValue) {
                            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        }
                        activity?.recreate()
                    }
                    showThemeDialog = false
                }
            )
        }

        if (showLanguageDialog) {
            val (languageEntries, languageValues) = remember {
                getLanguageOptions(context)
            }

            ListPreferenceDialog(
                title = stringResource(R.string.prefs_language),
                entries = languageEntries,
                entryValues = languageValues,
                selectedValue = currentLanguage,
                onDismiss = { showLanguageDialog = false },
                onValueSelected = { newValue ->
                    if (currentLanguage != newValue) {
                        scope.launch {
                            prefs.appLanguage.set(newValue)
                            context.restartApp()
                        }
                    }
                    showLanguageDialog = false
                }
            )
        }
    }
}

@Composable
fun getThemeLabel(key: String): String {
    return runCatching {
        Themes.entries.find { it.value == key }!!.labelRes
    }.fold(
        onSuccess = {
            stringResource(it)
        },
        onFailure = {
            key
        }
    )
}

@Composable
fun getLanguageLabel(value: String): String {
    val context = LocalContext.current
    return if (value == "system") {
        stringResource(R.string.prefs_language_system)
    } else {
        context.getLocaleOfCode(value).translate()
    }
}

fun getLanguageOptions(context: Context): Pair<Array<String>, Array<String>> {
    val locales: MutableList<String> = ArrayList()
    val languagesRaw = DetectedLocales.ALL.toSet()
    for (localeCode in languagesRaw) {
        val locale = context.getLocaleOfCode(localeCode)
        locales.add("${locale.translate()};$localeCode")
    }

    val entries = arrayOfNulls<String>(locales.size + 1)
    val entryVals = arrayOfNulls<String>(locales.size + 1)

    locales.forEachIndexed { i, locale ->
        val parts = locale.split(";")
        entries[i + 1] = parts[0]
        entryVals[i + 1] = parts[1]
    }

    entryVals[0] = "system"
    entries[0] = context.resources.getString(R.string.prefs_language_system)

    return Pair(
        entries.filterNotNull().toTypedArray(),
        entryVals.filterNotNull().toTypedArray()
    )
}