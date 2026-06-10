package com.cypressit.vault.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cypressit.vault.presentation.viewmodel.VaultEffect
import com.cypressit.vault.presentation.viewmodel.VaultViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun VaultScreen(
    modifier: Modifier = Modifier,
    viewModel: VaultViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is VaultEffect.ShowError -> snackBarHostState.showSnackbar(effect.message)
                VaultEffect.VaultCreated -> snackBarHostState.showSnackbar("Vault created")
                VaultEffect.VaultDeleted -> snackBarHostState.showSnackbar("Vault deleted")
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = MaterialTheme.colorScheme.background,
    ) { _ ->
        when {
            state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            else -> VaultContent(
                state = state,
                onIntent = viewModel::onIntent,
                modifier = modifier.fillMaxSize(),
            )
        }
    }
}
