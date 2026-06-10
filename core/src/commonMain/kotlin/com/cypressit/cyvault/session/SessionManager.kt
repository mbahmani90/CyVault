package com.cypressit.cyvault.session

interface SessionManager {
    suspend fun signOut(): Result<Unit>
}
