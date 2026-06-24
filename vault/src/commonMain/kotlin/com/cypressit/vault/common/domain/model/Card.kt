package com.cypressit.vault.common.domain.model

data class Card(
    val id: String,
    val bankName: String,
    val cardHolderName: String,
    val cardNumber: String,
    val cvv2: String,
    val expiryDate: String,  // MM/YY
    val balance: Double,
    val cardType: CardType,
)
