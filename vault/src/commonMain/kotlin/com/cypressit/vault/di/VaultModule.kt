package com.cypressit.vault.di

import com.cypressit.vault.data.remote.VaultApiService
import com.cypressit.vault.data.repository.VaultRepositoryImpl
import com.cypressit.vault.domain.repository.VaultRepository
import com.cypressit.vault.domain.usecase.CreateVaultUseCase
import com.cypressit.vault.domain.usecase.DeleteVaultUseCase
import com.cypressit.vault.domain.usecase.GetVaultsUseCase
import com.cypressit.vault.presentation.viewmodel.VaultViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val vaultModule = module {
    // Remote
    single { VaultApiService(get()) }

    // Repository
    single<VaultRepository> { VaultRepositoryImpl(get()) }

    // Use cases
    factory { GetVaultsUseCase(get()) }
    factory { CreateVaultUseCase(get()) }
    factory { DeleteVaultUseCase(get()) }

    // ViewModel
    viewModelOf(::VaultViewModel)
}
