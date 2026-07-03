package com.cypressit.vault.di

import com.cypressit.vault.addCard.domain.BankAppLauncher
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val vaultPlatformModule: Module = module {
    factory { BankAppLauncher(androidContext()) }
}
