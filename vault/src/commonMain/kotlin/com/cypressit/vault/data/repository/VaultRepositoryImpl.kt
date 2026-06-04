package com.cypressit.vault.data.repository

import com.cypressit.vault.data.dto.CreateVaultRequestDto
import com.cypressit.vault.data.mapper.toDomain
import com.cypressit.vault.data.remote.VaultApiService
import com.cypressit.vault.domain.model.Vault
import com.cypressit.vault.domain.repository.VaultRepository

class VaultRepositoryImpl(
    private val apiService: VaultApiService,
) : VaultRepository {

    override suspend fun getVaults(): Result<List<Vault>> =
        runCatching { apiService.getVaults().map { it.toDomain() } }

    override suspend fun createVault(
        title: String,
        username: String,
        password: String,
        url: String?,
        notes: String?,
    ): Result<Vault> =
        runCatching {
            apiService.createVault(
                CreateVaultRequestDto(
                    title = title,
                    username = username,
                    password = password,
                    url = url,
                    notes = notes,
                )
            ).toDomain()
        }

    override suspend fun deleteVault(id: String): Result<Unit> =
        runCatching { apiService.deleteVault(id) }
}
