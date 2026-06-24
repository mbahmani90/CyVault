package com.cypressit.vault.domain.usecase

import com.cypressit.vault.domain.model.Card
import com.cypressit.vault.domain.model.CardType
import com.cypressit.vault.domain.repository.CardRepository

class CreateCardUseCase(private val repository: CardRepository) {
    suspend operator fun invoke(
        bankName: String,
        cardHolderName: String,
        cardNumber: String,
        cvv2: String,
        expiryDate: String,
        balance: Double,
        cardType: CardType,
    ): Result<Card> = repository.createCard(
        bankName = bankName,
        cardHolderName = cardHolderName,
        cardNumber = cardNumber,
        cvv2 = cvv2,
        expiryDate = expiryDate,
        balance = balance,
        cardType = cardType,
    )
}
