package com.cypressit.cyvault

import androidx.compose.ui.window.ComposeUIViewController
import com.cypressit.cyvault.di.appModules
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {
    startKoin { modules(appModules) }
    App()
}
