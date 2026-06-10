package com.cypressit.vault.presentation.viewmodel

sealed class VaultHomeEffect {
    data object NavigateToLogin : VaultHomeEffect()
    data class ShowError(val message: String) : VaultHomeEffect()
}
