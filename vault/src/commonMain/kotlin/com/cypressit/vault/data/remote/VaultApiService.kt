package com.cypressit.vault.data.remote

import com.cypressit.vault.data.dto.CreateVaultRequestDto
import com.cypressit.vault.data.dto.VaultResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class VaultApiService(private val client: HttpClient) {

    companion object {
        private const val BASE_URL = "https://api.cyvault.com/v1/vaults"
    }

    suspend fun getVaults(): List<VaultResponseDto> =
        client.get(BASE_URL).body()

    suspend fun createVault(request: CreateVaultRequestDto): VaultResponseDto =
        client.post(BASE_URL) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun deleteVault(id: String) {
        client.delete("$BASE_URL/$id")
    }
}
