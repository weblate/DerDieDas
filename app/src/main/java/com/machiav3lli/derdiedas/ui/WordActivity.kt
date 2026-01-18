package com.machiav3lli.derdiedas.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import com.machiav3lli.derdiedas.ui.theme.AppTheme

class WordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                WordScreen(
                    onBack = { onBackPressed() }
                )
            }
        }
    }
}