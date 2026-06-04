package com.cypressit.vault.domain.usecase

import com.cypressit.vault.domain.repository.VaultRepository

class DeleteVaultUseCase(private val repository: VaultRepository) {
    suspend operator fun invoke(id: String): Result<Unit> =
        repository.deleteVault(id)
}
