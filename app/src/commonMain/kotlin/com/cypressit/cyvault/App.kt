package com.cypressit.cyvault

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.cypressit.authentication.presentation.ui.AuthScreen

@Composable
fun App() {
    MaterialTheme {
        var isAuthenticated by remember { mutableStateOf(false) }

        if (isAuthenticated) {
            HomeScreen()
        } else {
            AuthScreen(
                onAuthSuccess = { isAuthenticated = true }
            )
        }
    }
}

/** Temporary placeholder until the Home feature is built. */
@Composable
private fun HomeScreen() {
    Text("🎉 You're in! Home screen coming soon.")
}
