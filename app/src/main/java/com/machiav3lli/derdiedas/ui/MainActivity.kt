package com.machiav3lli.derdiedas.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.machiav3lli.derdiedas.PREFS_THEME
import com.machiav3lli.derdiedas.data.Noun
import com.machiav3lli.derdiedas.databinding.ActivityMainBinding
import com.machiav3lli.derdiedas.utils.DatabaseUtil
import com.machiav3lli.derdiedas.utils.FileUtils.getLines
import com.machiav3lli.derdiedas.utils.FileUtils.getNounList
import com.machiav3lli.derdiedas.utils.PrefsUtil.getPrefsString
import java.io.UnsupportedEncodingException
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setDayNightTheme(getPrefsString(PREFS_THEME))
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createDatabaseIfFirstRun()
    }

    override fun onStart() {
        super.onStart()
        setupOnClicks()
    }

    private fun setupOnClicks() {
        binding.practice.setOnClickListener {
            startActivity(Intent(baseContext, WordActivity::class.java))
        }
        binding.stats.setOnClickListener {
            startActivity(Intent(baseContext, StatsActivity::class.java))
        }
        binding.settings.setOnClickListener {
            startActivity(Intent(baseContext, SettingsActivity::class.java))
        }
    }

    private fun createDatabaseIfFirstRun() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if (prefs.getBoolean("firstrun", true)) {
            var nounsString: String? = null
            try {
                nounsString = getNounList(this)
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
            val nouns = getLines(nounsString!!)
            val nounsList: MutableList<Noun> = ArrayList()
            nouns.forEach {
                val noun = it.split(",").toTypedArray()[0]
                val gender = it.split(",").toTypedArray()[1]
                val nounObject = Noun(noun, gender, 0)
                nounsList.add(nounObject)
            }
            Thread {
                DatabaseUtil(this).allNouns = nounsList
            }.start()
            prefs.edit().putBoolean("firstrun", false).apply()
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