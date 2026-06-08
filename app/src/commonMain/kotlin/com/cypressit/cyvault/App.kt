package com.cypressit.cyvault

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cypressit.authentication.domain.usecase.SignOutUseCase
import com.cypressit.authentication.presentation.ui.AuthScreen
import com.cypressit.vault.presentation.ui.VaultScreen
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun App() {
    MaterialTheme {
        var isAuthenticated by remember { mutableStateOf(false) }

        if (isAuthenticated) {
            HomeScreen(onSignOut = { isAuthenticated = false })
        } else {
            AuthScreen(
                onAuthSuccess = { isAuthenticated = true }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(onSignOut: () -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val signOutUseCase: SignOutUseCase = koinInject()

    ModalNavigationDrawer(
        drawerState = drawerState,
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
                        scope.launch {
                            drawerState.close()
                            signOutUseCase()
                            onSignOut()
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp),
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("CyVault") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open Menu",
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            VaultScreen(modifier = Modifier.padding(paddingValues))
        }
    }
}
