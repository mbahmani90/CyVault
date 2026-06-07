package com.cypressit.cyvault.auth

interface IosAuthBridge {
    fun signIn(email: String, password: String, completion: (userId: String?, name: String?, error: String?) -> Unit)
    fun register(name: String, email: String, password: String, completion: (error: String?) -> Unit)
    fun confirmSignUp(email: String, code: String, completion: (error: String?) -> Unit)
    fun forgotPassword(email: String, completion: (error: String?) -> Unit)
}

object IosAuthBridgeHolder {
    var bridge: IosAuthBridge? = null
}
