package com.cypressit.vault.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VaultResponseDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("username") val username: String,
    @SerialName("password") val password: String,
    @SerialName("url") val url: String?,
    @SerialName("notes") val notes: String?,
)
