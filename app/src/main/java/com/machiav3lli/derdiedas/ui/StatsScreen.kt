package com.machiav3lli.derdiedas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.machiav3lli.derdiedas.R
import com.machiav3lli.derdiedas.ui.icons.Phosphor
import com.machiav3lli.derdiedas.ui.icons.phosphor.ArrowCircleLeft
import com.machiav3lli.derdiedas.ui.icons.phosphor.CircleWavyQuestion
import com.machiav3lli.derdiedas.utils.getNounsCount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(onBackPressed: () -> Unit) {
    val context = LocalContext.current
    var learnedWords by remember { mutableIntStateOf(0) }
    var allNouns by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val (learned, total) = context.getNounsCount()
            learnedWords = learned
            allNouns = total
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Phosphor.ArrowCircleLeft,
                            contentDescription = stringResource(R.string.dialog_back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.fully_learned_words_all_words),
                style = MaterialTheme.typography.titleLarge,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            ) {
                Text(
                    text = String.format(Locale.ENGLISH, "%d / %d", learnedWords, allNouns),
                    style = MaterialTheme.typography.titleLarge,
                )

                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Phosphor.CircleWavyQuestion,
                        contentDescription = stringResource(R.string.help),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(stringResource(R.string.full_words_title))
                },
                text = {
                    Text(stringResource(R.string.full_words_text))
                },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(stringResource(android.R.string.ok))
                    }
                }
            )
        }
    }
}