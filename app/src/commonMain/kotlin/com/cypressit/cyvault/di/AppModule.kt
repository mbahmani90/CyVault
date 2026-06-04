package com.cypressit.cyvault.di

import com.cypressit.authentication.di.authModule
import com.cypressit.vault.di.vaultModule
import org.koin.core.module.Module

/**
 * The single list passed to startKoin on every platform.
 * Add every new feature module here.
 */
val appModules: List<Module>
    get() = listOf(
        platformModule,
        networkModule,
        authModule,
        vaultModule,
    )
