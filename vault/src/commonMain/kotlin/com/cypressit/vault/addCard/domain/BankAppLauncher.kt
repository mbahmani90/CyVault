package com.cypressit.vault.addCard.domain

expect class BankAppLauncher {
    fun openBankUrl(url: String): Boolean
}
