package com.cypressit.vault.presentation.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.cypressit.vault.navigation.VaultRoute
import com.cypressit.vault.presentation.viewmodel.VaultHomeEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun VaultHomeScreenEffect(
    effects: Flow<VaultHomeEffect>,
    drawerState: DrawerState,
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    onNavigateToLogin: () -> Unit,
) {
    LaunchedEffect(Unit) {
        effects.collect { effect ->
            when (effect) {
                VaultHomeEffect.NavigateToLogin -> onNavigateToLogin()
                is VaultHomeEffect.ShowError -> snackBarHostState.showSnackbar(effect.message)
                VaultHomeEffect.OpenDrawer -> drawerState.open()
                VaultHomeEffect.CloseDrawer -> drawerState.close()
                VaultHomeEffect.NavigateToAddCard -> navController.navigate(VaultRoute.AddCard)
                VaultHomeEffect.NavigateBack -> navController.popBackStack()
            }
        }
    }
}
