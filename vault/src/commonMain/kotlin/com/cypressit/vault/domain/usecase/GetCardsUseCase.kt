package com.cypressit.vault.domain.usecase

import com.cypressit.vault.domain.model.Card
import com.cypressit.vault.domain.repository.CardRepository

class GetCardsUseCase(private val repository: CardRepository) {
    suspend operator fun invoke(): Result<List<Card>> = repository.getCards()
}
