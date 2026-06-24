package com.cypressit.vault.addCard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cypressit.vault.addCard.domain.usecase.CreateCardUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddCardViewModel(
    private val createCard: CreateCardUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AddCardState())
    val state: StateFlow<AddCardState> = _state.asStateFlow()

    private val _effect = Channel<AddCardEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: AddCardIntent) {
        when (intent) {
            is AddCardIntent.BankNameChanged -> _state.update { it.copy(bankName = intent.value) }
            is AddCardIntent.CardHolderNameChanged -> _state.update { it.copy(cardHolderName = intent.value) }
            is AddCardIntent.CardNumberChanged -> _state.update { it.copy(cardNumber = intent.value) }
            is AddCardIntent.Cvv2Changed -> _state.update { it.copy(cvv2 = intent.value) }
            is AddCardIntent.ExpiryDateChanged -> _state.update { it.copy(expiryDate = intent.value) }
            is AddCardIntent.BalanceChanged -> _state.update { it.copy(balance = intent.value) }
            is AddCardIntent.CardTypeChanged -> _state.update { it.copy(cardType = intent.value) }
            AddCardIntent.Submit -> submit()
        }
    }

    private fun submit() {
        val s = _state.value
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            createCard(
                bankName = s.bankName,
                cardHolderName = s.cardHolderName,
                cardNumber = s.cardNumber,
                cvv2 = s.cvv2,
                expiryDate = s.expiryDate,
                balance = s.balance.toDoubleOrNull() ?: 0.0,
                cardType = s.cardType,
            )
                .onSuccess { _effect.send(AddCardEffect.CardSaved) }
                .onFailure { err ->
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(AddCardEffect.ShowError(err.message ?: "Failed to save card"))
                }
        }
    }
}
