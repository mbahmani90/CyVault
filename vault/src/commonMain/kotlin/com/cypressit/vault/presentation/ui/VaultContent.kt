package com.cypressit.vault.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cypressit.vault.presentation.viewmodel.VaultIntent
import com.cypressit.vault.presentation.viewmodel.VaultState

@Composable
fun VaultContent(
    state: VaultState,
    onIntent: (VaultIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(state.items) { item ->
            ListItem(
                headlineContent = { Text(item.title) },
                supportingContent = { Text(item.username) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
