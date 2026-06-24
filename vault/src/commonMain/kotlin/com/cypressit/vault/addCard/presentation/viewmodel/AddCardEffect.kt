package com.cypressit.vault.addCard.presentation.viewmodel

sealed class AddCardEffect {
    data object CardSaved : AddCardEffect()
    data class ShowError(val message: String) : AddCardEffect()
}
