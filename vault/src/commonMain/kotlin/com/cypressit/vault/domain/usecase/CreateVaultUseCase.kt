package com.cypressit.vault.domain.usecase

import com.cypressit.vault.domain.model.Vault
import com.cypressit.vault.domain.repository.VaultRepository

class CreateVaultUseCase(private val repository: VaultRepository) {
    suspend operator fun invoke(
        title: String,
        username: String,
        password: String,
        url: String?,
        notes: String?,
    ): Result<Vault> = repository.createVault(title, username, password, url, notes)
}
