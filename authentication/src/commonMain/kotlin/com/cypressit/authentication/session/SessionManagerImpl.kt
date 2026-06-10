package com.cypressit.authentication.session

import com.cypressit.authentication.domain.repository.AuthRepository
import com.cypressit.cyvault.session.SessionManager

class SessionManagerImpl(
    private val repository: AuthRepository,
) : SessionManager {
    override suspend fun signOut(): Result<Unit> = repository.signOut()
}
