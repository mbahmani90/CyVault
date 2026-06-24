package com.cypressit.vault.di

import com.cypressit.vault.addCard.presentation.viewmodel.AddCardViewModel
import com.cypressit.vault.common.data.remote.CardApiService
import com.cypressit.vault.common.data.repository.CardRepositoryImpl
import com.cypressit.vault.common.domain.repository.CardRepository
import com.cypressit.vault.addCard.domain.usecase.CreateCardUseCase
import com.cypressit.vault.vaultList.domain.usecase.DeleteCardUseCase
import com.cypressit.vault.vaultList.domain.usecase.GetCardsUseCase
import com.cypressit.vault.presentation.viewmodel.VaultHomeViewModel
import com.cypressit.vault.vaultList.presentation.viewmodel.VaultListViewModel
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
    viewModelOf(::VaultHomeViewModel)
    viewModelOf(::VaultListViewModel)
    viewModelOf(::AddCardViewModel)
}
