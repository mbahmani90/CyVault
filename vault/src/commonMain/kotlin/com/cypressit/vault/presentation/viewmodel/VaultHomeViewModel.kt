package com.cypressit.vault.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cypressit.cyvault.session.SessionManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class VaultHomeViewModel(
    private val sessionManager: SessionManager,
) : ViewModel() {

    private val _effect = Channel<VaultHomeEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: VaultHomeIntent) {
        when (intent) {
            VaultHomeIntent.SignOut -> signOut()
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            sessionManager.signOut()
                .onSuccess {
                    _effect.send(VaultHomeEffect.NavigateToLogin)
                }
                .onFailure { error ->
                    _effect.send(VaultHomeEffect.ShowError(error.message ?: "Sign out failed"))
                }
        }
    }
}
