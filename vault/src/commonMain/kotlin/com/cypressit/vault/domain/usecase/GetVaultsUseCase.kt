package com.cypressit.vault.domain.usecase

import com.cypressit.vault.domain.model.Vault
import com.cypressit.vault.domain.repository.VaultRepository

class GetVaultsUseCase(private val repository: VaultRepository) {
    suspend operator fun invoke(): Result<List<Vault>> =
        repository.getVaults()
}
