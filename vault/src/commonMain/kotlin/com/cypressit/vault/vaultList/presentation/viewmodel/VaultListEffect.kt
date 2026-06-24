package com.cypressit.vault.vaultList.presentation.viewmodel

sealed class VaultListEffect {
    data class ShowError(val message: String) : VaultListEffect()
    data object CardDeleted : VaultListEffect()
}
