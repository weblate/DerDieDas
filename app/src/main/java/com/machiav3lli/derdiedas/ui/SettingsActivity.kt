package com.machiav3lli.derdiedas.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import com.machiav3lli.derdiedas.ui.theme.AppTheme

class SettingsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                SettingsScreen(
                    onBackPressed = { finish() }
                )
            }
        }
    }
}