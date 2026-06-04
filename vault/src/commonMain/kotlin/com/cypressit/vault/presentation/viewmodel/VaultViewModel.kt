package com.cypressit.vault.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cypressit.vault.domain.usecase.CreateVaultUseCase
import com.cypressit.vault.domain.usecase.DeleteVaultUseCase
import com.cypressit.vault.domain.usecase.GetVaultsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VaultViewModel(
    private val getVaults: GetVaultsUseCase,
    private val createVault: CreateVaultUseCase,
    private val deleteVault: DeleteVaultUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(VaultState())
    val state: StateFlow<VaultState> = _state.asStateFlow()

    private val _effect = Channel<VaultEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        onIntent(VaultIntent.LoadVaults)
    }

    fun onIntent(intent: VaultIntent) {
        when (intent) {
            VaultIntent.LoadVaults -> loadVaults()
            is VaultIntent.CreateVault -> createNewVault(intent)
            is VaultIntent.DeleteVault -> deleteExistingVault(intent.id)
        }
    }

    private fun loadVaults() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getVaults()
                .onSuccess { items -> _state.update { it.copy(isLoading = false, items = items) } }
                .onFailure { err ->
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(VaultEffect.ShowError(err.message ?: "Something went wrong"))
                }
        }
    }

    private fun createNewVault(intent: VaultIntent.CreateVault) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            createVault(intent.title, intent.username, intent.password, intent.url, intent.notes)
                .onSuccess {
                    _effect.send(VaultEffect.VaultCreated)
                    loadVaults()
                }
                .onFailure { err ->
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(VaultEffect.ShowError(err.message ?: "Something went wrong"))
                }
        }
    }

    private fun deleteExistingVault(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            deleteVault(id)
                .onSuccess {
                    _effect.send(VaultEffect.VaultDeleted)
                    loadVaults()
                }
                .onFailure { err ->
                    _state.update { it.copy(isLoading = false) }
                    _effect.send(VaultEffect.ShowError(err.message ?: "Something went wrong"))
                }
        }
    }
}
