package com.machiav3lli.derdiedas.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.machiav3lli.derdiedas.R
import com.machiav3lli.derdiedas.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, SettingsFragment())
            .commit()
        binding.back.setOnClickListener { onBackPressed() }
    }
}