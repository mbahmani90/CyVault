package com.cypressit.vault.domain.repository

import com.cypressit.vault.domain.model.Vault

interface VaultRepository {
    suspend fun getVaults(): Result<List<Vault>>
    suspend fun createVault(
        title: String,
        username: String,
        password: String,
        url: String?,
        notes: String?,
    ): Result<Vault>
    suspend fun deleteVault(id: String): Result<Unit>
}
