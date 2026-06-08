package com.cypressit.authentication.data.remote

import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.kotlin.core.Amplify
import com.cypressit.authentication.domain.model.User

actual class AuthApiService actual constructor() {

    actual suspend fun login(email: String, password: String): User {
        val result = Amplify.Auth.signIn(email, password)
        if (!result.isSignedIn) {
            throw Exception("Sign in failed: additional steps required")
        }
        return fetchCurrentUserAttributes(email)
    }

    actual suspend fun register(name: String, email: String, password: String) {
        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), email)
            .userAttribute(AuthUserAttributeKey.name(), name)
            .build()
        Amplify.Auth.signUp(email, password, options)
    }

    actual suspend fun confirmSignUp(email: String, code: String) {
        Amplify.Auth.confirmSignUp(email, code)
    }

    actual suspend fun forgotPassword(email: String) {
        Amplify.Auth.resetPassword(email)
    }

    actual suspend fun signOut() {
        Amplify.Auth.signOut()
    }

    actual suspend fun getCurrentUser(): User? {
        return try {
            val session = Amplify.Auth.fetchAuthSession()
            if (!session.isSignedIn) return null
            fetchCurrentUserAttributes(null)
        } catch (e: Exception) {
            null
        }
    }

    actual suspend fun signInWithGoogle(): User {
        val activity = ActivityProvider.activity
            ?: throw Exception("No activity available for Google Sign-In")
        Amplify.Auth.signInWithSocialWebUI(com.amplifyframework.auth.AuthProvider.google(), activity)
        return fetchCurrentUserAttributes(null)
    }

    private suspend fun fetchCurrentUserAttributes(fallbackEmail: String?): User {
        val attributes = Amplify.Auth.fetchUserAttributes()
        val sub = attributes.find { it.key.keyString == "sub" }?.value ?: ""
        val name = attributes.find { it.key.keyString == "name" }?.value ?: ""
        val email = attributes.find { it.key.keyString == "email" }?.value ?: fallbackEmail ?: ""
        return User(id = sub, name = name, email = email, token = "")
    }
}
