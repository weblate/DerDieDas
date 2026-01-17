package com.machiav3lli.derdiedas.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.machiav3lli.derdiedas.R

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ActionButton(
            text = stringResource(R.string.practice),
        ) {
            context.startActivity(Intent(context, WordActivity::class.java))
        }
        ActionButton(
            text = stringResource(R.string.stats),
        ) {
            context.startActivity(Intent(context, StatsActivity::class.java))
        }
        ActionButton(
            text = stringResource(R.string.settings),
        ) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }
}