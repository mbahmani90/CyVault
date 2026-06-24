package com.cypressit.vault.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cypressit.vault.domain.usecase.CreateCardUseCase
import com.cypressit.vault.domain.usecase.DeleteCardUseCase
import com.cypressit.vault.domain.usecase.GetCardsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VaultViewModel(
    private val getCards: GetCardsUseCase,
    private val createCard: CreateCardUseCase,
    private val deleteCard: DeleteCardUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(VaultState())
    val state: StateFlow<VaultState> = _state.asStateFlow()

    private val _effect = Channel<VaultEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        onIntent(VaultIntent.LoadCards)
    }

    fun onIntent(intent: VaultIntent) {
        when (intent) {
            VaultIntent.LoadCards -> loadCards()
            is VaultIntent.CreateCard -> createNewCard(intent)
            is VaultIntent.DeleteCard -> deleteExistingCard(intent.id)
        }
    }

    private fun loadCards() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getCards()
                .onSuccess { cards ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            cards = cards,
                            totalBalance = cards.sumOf { card -> card.balance },
                        )
                    }
                }
                .onFailure { err ->
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(VaultEffect.ShowError(err.message ?: "Something went wrong"))
                }
        }
    }

    private fun createNewCard(intent: VaultIntent.CreateCard) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            createCard(
                bankName = intent.bankName,
                cardHolderName = intent.cardHolderName,
                cardNumber = intent.cardNumber,
                cvv2 = intent.cvv2,
                expiryDate = intent.expiryDate,
                balance = intent.balance,
                cardType = intent.cardType,
            )
                .onSuccess {
                    _effect.send(VaultEffect.CardCreated)
                    loadCards()
                }
                .onFailure { err ->
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(VaultEffect.ShowError(err.message ?: "Something went wrong"))
                }
        }
    }

    private fun deleteExistingCard(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            deleteCard(id)
                .onSuccess {
                    _effect.send(VaultEffect.CardDeleted)
                    loadCards()
                }
                .onFailure { err ->
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(VaultEffect.ShowError(err.message ?: "Something went wrong"))
                }
        }
    }
}
