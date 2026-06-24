package com.cypressit.vault.addCard.presentation.viewmodel

import com.cypressit.vault.common.domain.model.CardType

sealed class AddCardIntent {
    data class BankNameChanged(val value: String) : AddCardIntent()
    data class CardHolderNameChanged(val value: String) : AddCardIntent()
    data class CardNumberChanged(val value: String) : AddCardIntent()
    data class Cvv2Changed(val value: String) : AddCardIntent()
    data class ExpiryDateChanged(val value: String) : AddCardIntent()
    data class BalanceChanged(val value: String) : AddCardIntent()
    data class CardTypeChanged(val value: CardType) : AddCardIntent()
    data object Submit : AddCardIntent()
}
