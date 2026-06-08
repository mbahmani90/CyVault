package com.cypressit.authentication.di

import com.cypressit.authentication.data.remote.AuthApiService
import com.cypressit.authentication.data.repository.AuthRepositoryImpl
import com.cypressit.authentication.domain.repository.AuthRepository
import com.cypressit.authentication.domain.usecase.ConfirmSignUpUseCase
import com.cypressit.authentication.domain.usecase.ForgotPasswordUseCase
import com.cypressit.authentication.domain.usecase.GetCurrentUserUseCase
import com.cypressit.authentication.domain.usecase.LoginUseCase
import com.cypressit.authentication.domain.usecase.RegisterUseCase
import com.cypressit.authentication.domain.usecase.SignInWithGoogleUseCase
import com.cypressit.authentication.domain.usecase.SignOutUseCase
import com.cypressit.authentication.presentation.viewmodel.AuthViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    // Remote
    single { AuthApiService() }

    // Repository
    single<AuthRepository> { AuthRepositoryImpl(get()) }

    // Use cases
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { ConfirmSignUpUseCase(get()) }
    factory { ForgotPasswordUseCase(get()) }
    factory { SignOutUseCase(get()) }
    factory { GetCurrentUserUseCase(get()) }
    factory { SignInWithGoogleUseCase(get()) }

    // ViewModel
    viewModelOf(::AuthViewModel)
}
