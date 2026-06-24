package com.cypressit.vault.domain.usecase

import com.cypressit.vault.domain.repository.CardRepository

class DeleteCardUseCase(private val repository: CardRepository) {
    suspend operator fun invoke(id: String): Result<Unit> = repository.deleteCard(id)
}
