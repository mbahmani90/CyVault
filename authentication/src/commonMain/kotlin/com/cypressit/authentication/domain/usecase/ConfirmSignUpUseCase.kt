package com.cypressit.authentication.domain.usecase

import com.cypressit.authentication.domain.repository.AuthRepository

class ConfirmSignUpUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, code: String): Result<Unit> =
        repository.confirmSignUp(email, code)
}
