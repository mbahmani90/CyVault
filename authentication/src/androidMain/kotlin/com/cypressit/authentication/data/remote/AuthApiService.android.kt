package com.cypressit.authentication.data.remote

import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.kotlin.core.Amplify
import com.cypressit.authentication.domain.model.User

actual class AuthApiService actual constructor() {

    actual suspend fun login(email: String, password: String): User {
        val result = Amplify.Auth.signIn(email, password)
        if (!result.isSignedIn) {
            throw Exception("Sign in failed: additional steps required")
        }
        val attributes = Amplify.Auth.fetchUserAttributes()
        val sub = attributes.find { it.key.keyString == "sub" }?.value ?: ""
        val name = attributes.find { it.key.keyString == "name" }?.value ?: ""
        return User(
            id = sub,
            name = name,
            email = email,
            token = "",
        )
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
}
