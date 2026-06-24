package com.cypressit.vault.navigation

import kotlinx.serialization.Serializable

sealed class VaultRoute {

    @Serializable
    data object List : VaultRoute()

    @Serializable
    data object AddCard : VaultRoute()
}
