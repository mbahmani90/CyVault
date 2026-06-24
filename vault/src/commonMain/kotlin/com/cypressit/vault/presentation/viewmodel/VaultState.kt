package com.cypressit.vault.presentation.viewmodel

import com.cypressit.vault.domain.model.Card

data class VaultState(
    val isLoading: Boolean = false,
    val cards: List<Card> = emptyList(),
    val totalBalance: Double = 0.0,
    val error: String? = null,
)
