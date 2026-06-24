package com.cypressit.vault.presentation.viewmodel

sealed class VaultEffect {
    data class ShowError(val message: String) : VaultEffect()
    data object CardCreated : VaultEffect()
    data object CardDeleted : VaultEffect()
}
