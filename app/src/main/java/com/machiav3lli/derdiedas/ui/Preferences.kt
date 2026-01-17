package com.machiav3lli.derdiedas.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PreferenceItem(
    title: String,
    summary: String,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(text = title) },
        supportingContent = { Text(text = summary) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    )
}