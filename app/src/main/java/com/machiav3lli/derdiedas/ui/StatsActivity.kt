package com.machiav3lli.derdiedas.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import com.machiav3lli.derdiedas.ui.theme.AppTheme

class StatsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                StatsScreen(
                    onBackPressed = { finish() }
                )
            }
        }
    }
}