package com.cypressit.authentication.domain.usecase

import com.cypressit.authentication.domain.repository.AuthRepository

class ForgotPasswordUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String): Result<Unit> =
        repository.forgotPassword(email)
}
