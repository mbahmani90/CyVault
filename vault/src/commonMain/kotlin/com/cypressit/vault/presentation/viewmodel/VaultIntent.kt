package com.cypressit.vault.presentation.viewmodel

sealed class VaultIntent {
    object LoadVaults : VaultIntent()
    data class CreateVault(
        val title: String,
        val username: String,
        val password: String,
        val url: String?,
        val notes: String?,
    ) : VaultIntent()
    data class DeleteVault(val id: String) : VaultIntent()
}
