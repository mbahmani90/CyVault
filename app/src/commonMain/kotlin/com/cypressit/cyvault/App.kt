package com.cypressit.cyvault

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.cypressit.authentication.presentation.ui.AuthScreen

@Composable
fun App() {
    MaterialTheme {
        var isAuthenticated by remember { mutableStateOf(false) }
        var sessionKey by remember { mutableStateOf(0) }

        if (isAuthenticated) {
            VaultHomeScreen(onSignOut = {
                isAuthenticated = false
                sessionKey++
            })
        } else {
            key(sessionKey) {
                AuthScreen(
                    onAuthSuccess = { isAuthenticated = true }
                )
            }
        }
    }
}
