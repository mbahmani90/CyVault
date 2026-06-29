package com.cypressit.vault.presentation.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cypressit.vault.addCard.presentation.ui.AddCardScreen
import com.cypressit.vault.navigation.VaultRoute
import com.cypressit.vault.presentation.viewmodel.VaultHomeIntent
import com.cypressit.vault.presentation.viewmodel.VaultHomeViewModel
import com.cypressit.vault.vaultList.presentation.ui.VaultListScreen
import cyvault.vault.generated.resources.Res
import cyvault.vault.generated.resources.vault_home_back_description
import cyvault.vault.generated.resources.vault_home_open_menu_description
import cyvault.vault.generated.resources.vault_home_sign_out
import cyvault.vault.generated.resources.vault_home_sign_out_description
import cyvault.vault.generated.resources.vault_home_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaultHomeScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: VaultHomeViewModel = koinViewModel(),
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val snackBarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val isOnVaultScreen = currentBackStack?.destination?.route
        ?.contains(VaultRoute.List::class.simpleName ?: "") != false

    VaultHomeScreenEffect(
        effects = viewModel.effect,
        drawerState = drawerState,
        navController = navController,
        snackBarHostState = snackBarHostState,
        onNavigateToLogin = onNavigateToLogin,
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = isOnVaultScreen,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(24.dp))
                Text(
                    text = stringResource(Res.string.vault_home_title),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
                Spacer(Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(Modifier.height(8.dp))
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = stringResource(Res.string.vault_home_sign_out_description),
                        )
                    },
                    label = { Text(stringResource(Res.string.vault_home_sign_out)) },
                    selected = false,
                    onClick = { viewModel.onIntent(VaultHomeIntent.SignOut) },
                    modifier = Modifier.padding(horizontal = 12.dp),
                )
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(Res.string.vault_home_title)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (isOnVaultScreen) viewModel.onIntent(VaultHomeIntent.OpenDrawer)
                            else viewModel.onIntent(VaultHomeIntent.NavigateBack)
                        }) {
                            Icon(
                                imageVector = if (isOnVaultScreen) Icons.Default.Menu
                                    else Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = if (isOnVaultScreen) stringResource(Res.string.vault_home_open_menu_description)
                                    else stringResource(Res.string.vault_home_back_description),
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = VaultRoute.List,
                modifier = Modifier.padding(paddingValues),
            ) {
                composable<VaultRoute.List> {
                    VaultListScreen(onAddCard = { viewModel.onIntent(VaultHomeIntent.NavigateToAddCard) })
                }
                composable<VaultRoute.AddCard> {
                    AddCardScreen(
                        onBack = { viewModel.onIntent(VaultHomeIntent.NavigateBack) },
                        onCardSaved = { viewModel.onIntent(VaultHomeIntent.NavigateBack) },
                    )
                }
            }
        }
    }
}
