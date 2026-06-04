package com.cypressit.authentication.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordRequestDto(
    @SerialName("email") val email: String,
)
