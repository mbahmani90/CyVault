package com.cypressit.authentication.domain.usecase

import com.cypressit.authentication.domain.model.User
import com.cypressit.authentication.domain.repository.AuthRepository

class SignInWithGoogleUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): Result<User> = repository.signInWithGoogle()
}
