package com.machiav3lli.derdiedas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.machiav3lli.derdiedas.R
import com.machiav3lli.derdiedas.ui.navigation.NavRoute

@Composable
fun MainScreen(navigator: (NavRoute) -> Unit) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ActionButton(
                text = stringResource(R.string.practice),
            ) {
                navigator(NavRoute.Word)
            }
            ActionButton(
                text = stringResource(R.string.stats),
            ) {
                navigator(NavRoute.Stats)
            }
            ActionButton(
                text = stringResource(R.string.settings),
            ) {
                navigator(NavRoute.Settings)
            }
        }
    }
}