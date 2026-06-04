package com.cypressit.authentication.data.remote

import com.cypressit.authentication.data.dto.LoginRequestDto
import com.cypressit.authentication.data.dto.LoginResponseDto
import com.cypressit.authentication.data.dto.RegisterRequestDto
import com.cypressit.authentication.data.dto.RegisterResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApiService(private val client: HttpClient) {

    companion object {
        private const val BASE_URL = "https://api.cyvault.com/v1"
    }

    suspend fun login(request: LoginRequestDto): LoginResponseDto =
        client.post("$BASE_URL/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun register(request: RegisterRequestDto): RegisterResponseDto =
        client.post("$BASE_URL/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
}
