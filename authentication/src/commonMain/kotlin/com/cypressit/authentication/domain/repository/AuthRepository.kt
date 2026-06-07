package com.cypressit.authentication.domain.repository

import com.cypressit.authentication.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(name: String, email: String, password: String): Result<Unit>
    suspend fun confirmSignUp(email: String, code: String): Result<Unit>
    suspend fun forgotPassword(email: String): Result<Unit>
}
