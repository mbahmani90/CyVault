package com.cypressit.vault.presentation.viewmodel

import com.cypressit.vault.domain.model.CardType

sealed class VaultIntent {
    data object LoadCards : VaultIntent()
    data class CreateCard(
        val bankName: String,
        val cardHolderName: String,
        val cardNumber: String,
        val cvv2: String,
        val expiryDate: String,
        val balance: Double,
        val cardType: CardType,
    ) : VaultIntent()
    data class DeleteCard(val id: String) : VaultIntent()
}
