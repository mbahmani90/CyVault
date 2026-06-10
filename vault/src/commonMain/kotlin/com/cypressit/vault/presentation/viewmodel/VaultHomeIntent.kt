package com.cypressit.vault.presentation.viewmodel

sealed class VaultHomeIntent {
    data object SignOut : VaultHomeIntent()
}
