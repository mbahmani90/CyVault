package com.cypressit.vault.presentation.viewmodel

sealed class VaultHomeIntent {
    data object SignOut : VaultHomeIntent()
    data object OpenDrawer : VaultHomeIntent()
    data object NavigateToAddCard : VaultHomeIntent()
    data object NavigateBack : VaultHomeIntent()
}
