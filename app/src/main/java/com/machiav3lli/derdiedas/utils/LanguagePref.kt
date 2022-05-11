package com.machiav3lli.derdiedas.utils

import android.content.Context
import android.util.AttributeSet
import androidx.preference.ListPreference
import com.machiav3lli.derdiedas.BuildConfig
import com.machiav3lli.derdiedas.PREFS_LANG_SYSTEM
import com.machiav3lli.derdiedas.R

class LanguagePref(context: Context, attrs: AttributeSet?) : ListPreference(context, attrs) {

    init {
        loadLangs(context)
    }

    private fun loadLangs(context: Context) {
        setDefaultValue(PREFS_LANG_SYSTEM)

        val locales: MutableList<String> = ArrayList()
        val languagesRaw = BuildConfig.DETECTED_LOCALES.sorted()
        for (localeCode in languagesRaw) {
            val locale = context.getLocaleOfCode(localeCode)
            locales.add("${locale.translate()};$localeCode")
        }

        val entries = arrayOfNulls<String>(locales.size + 1)
        val entryVals = arrayOfNulls<String>(locales.size + 1)
        locales.forEachIndexed { i, locale ->
            entries[i + 1] = locale.split(";")[0]
            entryVals[i + 1] = locale.split(";")[1]
        }
        entryVals[0] = PREFS_LANG_SYSTEM
        entries[0] = context.resources.getString(R.string.prefs_language_system)
        setEntries(entries)
        entryValues = entryVals
    }

    override fun getSummary(): CharSequence = context.getLocaleOfCode(value).translate()
}