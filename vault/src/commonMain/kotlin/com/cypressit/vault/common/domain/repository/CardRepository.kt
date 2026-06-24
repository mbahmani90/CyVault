package com.cypressit.vault.common.domain.repository

import com.cypressit.vault.common.domain.model.Card
import com.cypressit.vault.common.domain.model.CardType

interface CardRepository {
    suspend fun getCards(): Result<List<Card>>
    suspend fun createCard(
        bankName: String,
        cardHolderName: String,
        cardNumber: String,
        cvv2: String,
        expiryDate: String,
        balance: Double,
        cardType: CardType,
    ): Result<Card>
    suspend fun deleteCard(id: String): Result<Unit>
}
