package com.cypressit.vault.addCard.domain.model

data class BankAccount(
    val id: String,
    val name: String,
    val ownerName: String?,
    val iban: String?,
    val balance: Double,
    val currency: String,
)
