package com.cypressit.vault.presentation.viewmodel

sealed class VaultEffect {
    data class ShowError(val message: String) : VaultEffect()
    object VaultCreated : VaultEffect()
    object VaultDeleted : VaultEffect()
}
