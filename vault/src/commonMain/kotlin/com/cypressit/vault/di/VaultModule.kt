package com.cypressit.vault.di

import com.cypressit.vault.data.remote.CardApiService
import com.cypressit.vault.data.repository.CardRepositoryImpl
import com.cypressit.vault.domain.repository.CardRepository
import com.cypressit.vault.domain.usecase.CreateCardUseCase
import com.cypressit.vault.domain.usecase.DeleteCardUseCase
import com.cypressit.vault.domain.usecase.GetCardsUseCase
import com.cypressit.vault.presentation.viewmodel.VaultHomeViewModel
import com.cypressit.vault.presentation.viewmodel.VaultViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val vaultModule = module {
    // Remote
    single { CardApiService(get()) }

    // Repository
    single<CardRepository> { CardRepositoryImpl(get()) }

    // Use cases
    factory { GetCardsUseCase(get()) }
    factory { CreateCardUseCase(get()) }
    factory { DeleteCardUseCase(get()) }

    // ViewModels
    viewModelOf(::VaultViewModel)
    viewModelOf(::VaultHomeViewModel)
}
