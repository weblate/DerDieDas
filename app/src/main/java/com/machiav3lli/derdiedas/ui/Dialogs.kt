package com.machiav3lli.derdiedas.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ListPreferenceDialog(
    title: String,
    entries: Array<String>,
    entryValues: Array<String>,
    selectedValue: String,
    onDismiss: () -> Unit,
    onValueSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            Card(
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                ),
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    itemsIndexed(entries) { i, item ->
                        val value = entryValues[i]
                        ListItem(
                            colors = ListItemDefaults.colors(
                                containerColor = Color.Transparent,
                            ),
                            headlineContent = { Text(text = item) },
                            leadingContent = {
                                RadioButton(
                                    selected = value == selectedValue,
                                    onClick = { onValueSelected(value) }
                                )
                            },
                            modifier = Modifier
                                .clickable { onValueSelected(value) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(android.R.string.cancel))
            }
        }
    )
}