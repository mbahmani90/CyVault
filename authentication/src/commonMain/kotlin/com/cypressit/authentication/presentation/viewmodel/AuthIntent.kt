package com.cypressit.authentication.presentation.viewmodel

sealed class AuthIntent {
    data class NameChanged(val name: String) : AuthIntent()
    data class EmailChanged(val email: String) : AuthIntent()
    data class PasswordChanged(val password: String) : AuthIntent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : AuthIntent()
    object SubmitLogin : AuthIntent()
    object SubmitRegister : AuthIntent()
    object ToggleMode : AuthIntent()
    object ShowForgotPassword : AuthIntent()
    object BackToLogin : AuthIntent()
    object SubmitForgotPassword : AuthIntent()
}
