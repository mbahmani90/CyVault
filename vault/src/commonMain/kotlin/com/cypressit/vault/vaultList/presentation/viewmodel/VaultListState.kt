package com.cypressit.vault.vaultList.presentation.viewmodel

import com.cypressit.vault.common.domain.model.Card

data class VaultListState(
    val isLoading: Boolean = false,
    val cards: List<Card> = emptyList(),
    val totalBalance: Double = 0.0,
    val error: String? = null,
)
