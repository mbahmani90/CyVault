package com.cypressit.authentication.domain.usecase

import com.cypressit.authentication.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(name: String, email: String, password: String): Result<Unit> =
        repository.register(name, email, password)
}
