package com.cypressit.cyvault

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cypressit.authentication.domain.usecase.SignOutUseCase
import com.cypressit.authentication.presentation.ui.AuthScreen
import com.cypressit.cyvault.navigation.Route
import com.cypressit.vault.presentation.ui.VaultHomeScreen
import org.koin.compose.koinInject

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val signOutUseCase: SignOutUseCase = koinInject()

        NavHost(
            navController = navController,
            startDestination = Route.Login,
        ) {
            composable<Route.Login> {
                AuthScreen(
                    onAuthSuccess = {
                        navController.navigate(Route.Vault) {
                            popUpTo(Route.Login) { inclusive = true }
                        }
                    }
                )
            }

            composable<Route.Vault> {
                VaultHomeScreen(
                    onSignOut = {
                        signOutUseCase()
                        navController.navigate(Route.Login) {
                            popUpTo(Route.Vault) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
