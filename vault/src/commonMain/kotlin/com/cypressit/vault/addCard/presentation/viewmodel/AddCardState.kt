package com.cypressit.vault.addCard.presentation.viewmodel

import com.cypressit.vault.common.domain.model.CardType

data class AddCardState(
    val bankName: String = "",
    val cardHolderName: String = "",
    val cardNumber: String = "",
    val cvv2: String = "",
    val expiryDate: String = "",
    val balance: String = "",
    val cardType: CardType = CardType.OTHER,
    val isLoading: Boolean = false,
)
