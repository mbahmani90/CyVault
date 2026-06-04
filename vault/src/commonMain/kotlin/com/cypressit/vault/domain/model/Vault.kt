package com.cypressit.vault.domain.model

data class Vault(
    val id: String,
    val title: String,
    val username: String,
    val password: String,
    val url: String?,
    val notes: String?,
)
