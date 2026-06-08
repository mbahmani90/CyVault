package com.cypressit.authentication.data.repository

import com.cypressit.authentication.data.remote.AuthApiService
import com.cypressit.authentication.domain.model.User
import com.cypressit.authentication.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val apiService: AuthApiService,
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> =
        runCatching { apiService.login(email, password) }

    override suspend fun register(name: String, email: String, password: String): Result<Unit> =
        runCatching { apiService.register(name, email, password) }

    override suspend fun confirmSignUp(email: String, code: String): Result<Unit> =
        runCatching { apiService.confirmSignUp(email, code) }

    override suspend fun forgotPassword(email: String): Result<Unit> =
        runCatching { apiService.forgotPassword(email) }

    override suspend fun signOut(): Result<Unit> =
        runCatching { apiService.signOut() }

    override suspend fun getCurrentUser(): User? =
        apiService.getCurrentUser()

    override suspend fun signInWithGoogle(): Result<User> =
        runCatching { apiService.signInWithGoogle() }
}
