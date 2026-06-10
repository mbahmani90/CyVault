package com.cypressit.cyvault.navigation

import kotlinx.serialization.Serializable

sealed class Route {

    @Serializable
    data object Login : Route()

    @Serializable
    data object Vault : Route()
}
