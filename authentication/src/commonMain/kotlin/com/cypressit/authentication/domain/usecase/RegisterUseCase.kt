package com.cypressit.authentication.domain.usecase

import com.cypressit.authentication.domain.model.User
import com.cypressit.authentication.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(name: String, email: String, password: String): Result<User> =
        repository.register(name, email, password)
}
