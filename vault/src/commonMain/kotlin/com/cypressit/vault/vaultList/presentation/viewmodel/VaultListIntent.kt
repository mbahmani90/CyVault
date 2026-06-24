package com.cypressit.vault.vaultList.presentation.viewmodel

sealed class VaultListIntent {
    data object LoadCards : VaultListIntent()
    data class DeleteCard(val id: String) : VaultListIntent()
}
