package com.cypressit.vault.presentation.viewmodel

import com.cypressit.vault.domain.model.Vault

data class VaultState(
    val isLoading: Boolean = false,
    val items: List<Vault> = emptyList(),
    val error: String? = null,
)
