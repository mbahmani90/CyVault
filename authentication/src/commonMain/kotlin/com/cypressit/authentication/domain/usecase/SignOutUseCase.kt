package com.cypressit.authentication.domain.usecase

import com.cypressit.authentication.domain.repository.AuthRepository

class SignOutUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): Result<Unit> = repository.signOut()
}
