package com.cypressit.vault.data.mapper

import com.cypressit.vault.data.dto.CardResponseDto
import com.cypressit.vault.domain.model.Card
import com.cypressit.vault.domain.model.CardType

fun CardResponseDto.toDomain(): Card = Card(
    id = id,
    bankName = bankName,
    cardHolderName = cardHolderName,
    cardNumber = cardNumber,
    cvv2 = cvv2,
    expiryDate = expiryDate,
    balance = balance,
    cardType = CardType.entries.firstOrNull { it.name == cardType } ?: CardType.OTHER,
)
