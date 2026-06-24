package com.cypressit.vault.vaultList.domain.usecase

import com.cypressit.vault.common.domain.repository.CardRepository

class DeleteCardUseCase(private val repository: CardRepository) {
    suspend operator fun invoke(id: String): Result<Unit> = repository.deleteCard(id)
}
