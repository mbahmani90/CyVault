package com.cypressit.authentication.data.repository

import com.cypressit.authentication.data.dto.LoginRequestDto
import com.cypressit.authentication.data.dto.RegisterRequestDto
import com.cypressit.authentication.data.mapper.toDomain
import com.cypressit.authentication.data.remote.AuthApiService
import com.cypressit.authentication.domain.model.User
import com.cypressit.authentication.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val apiService: AuthApiService,
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> =
        runCatching {
            apiService.login(LoginRequestDto(email, password)).toDomain()
        }

    override suspend fun register(name: String, email: String, password: String): Result<User> =
        runCatching {
            apiService.register(RegisterRequestDto(name, email, password)).toDomain()
        }
}
