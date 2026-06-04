package com.cypressit.authentication.data.mapper

import com.cypressit.authentication.data.dto.LoginResponseDto
import com.cypressit.authentication.data.dto.RegisterResponseDto
import com.cypressit.authentication.domain.model.User

fun LoginResponseDto.toDomain(): User = User(
    id = id,
    name = name,
    email = email,
    token = token,
)

fun RegisterResponseDto.toDomain(): User = User(
    id = id,
    name = name,
    email = email,
    token = token,
)
