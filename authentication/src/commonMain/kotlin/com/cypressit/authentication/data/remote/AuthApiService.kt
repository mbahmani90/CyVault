package com.cypressit.authentication.data.remote

import com.cypressit.authentication.domain.model.User

expect class AuthApiService() {
    suspend fun login(email: String, password: String): User
    suspend fun register(name: String, email: String, password: String)
    suspend fun confirmSignUp(email: String, code: String)
    suspend fun forgotPassword(email: String)
}
