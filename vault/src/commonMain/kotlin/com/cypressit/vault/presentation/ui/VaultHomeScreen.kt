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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cypressit.vault.addCard.presentation.ui.AddCardScreen
import com.cypressit.vault.navigation.VaultRoute
import com.cypressit.vault.presentation.viewmodel.VaultHomeEffect
import com.cypressit.vault.presentation.viewmodel.VaultHomeIntent
import com.cypressit.vault.presentation.viewmodel.VaultHomeViewModel
import com.cypressit.vault.vaultList.presentation.ui.VaultListScreen
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaultHomeScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: VaultHomeViewModel = koinViewModel(),
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val isOnList = currentBackStack?.destination?.route
        ?.contains(VaultRoute.List::class.simpleName ?: "") != false

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                VaultHomeEffect.NavigateToLogin -> onNavigateToLogin()
                is VaultHomeEffect.ShowError -> snackBarHostState.showSnackbar(effect.message)
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = isOnList,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "CyVault",
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
                            contentDescription = "Sign Out",
                        )
                    },
                    label = { Text("Sign Out") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.onIntent(VaultHomeIntent.SignOut)
                    },
                    modifier = Modifier.padding(horizontal = 12.dp),
                )
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text("CyVault") },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (isOnList) scope.launch { drawerState.open() }
                            else navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = if (isOnList) Icons.Default.Menu
                                    else Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = if (isOnList) "Open Menu" else "Back",
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
                    VaultListScreen(onAddCard = { navController.navigate(VaultRoute.AddCard) })
                }
                composable<VaultRoute.AddCard> {
                    AddCardScreen(
                        onBack = { navController.popBackStack() },
                        onCardSaved = { navController.popBackStack() },
                    )
                }
            }
        }
    }
}
