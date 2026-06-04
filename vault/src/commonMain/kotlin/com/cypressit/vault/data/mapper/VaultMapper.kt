package com.cypressit.vault.data.mapper

import com.cypressit.vault.data.dto.VaultResponseDto
import com.cypressit.vault.domain.model.Vault

fun VaultResponseDto.toDomain(): Vault = Vault(
    id = id,
    title = title,
    username = username,
    password = password,
    url = url,
    notes = notes,
)
