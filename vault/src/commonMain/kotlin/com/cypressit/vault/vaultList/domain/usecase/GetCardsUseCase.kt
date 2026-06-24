package com.cypressit.vault.vaultList.domain.usecase

import com.cypressit.vault.common.domain.model.Card
import com.cypressit.vault.common.domain.repository.CardRepository

class GetCardsUseCase(private val repository: CardRepository) {
    suspend operator fun invoke(): Result<List<Card>> = repository.getCards()
}
