package com.cypressit.vault.addCard.domain.usecase

import com.cypressit.vault.addCard.data.remote.YapilyService
import com.cypressit.vault.addCard.domain.model.BankAccount

class GetAccountsUseCase(private val service: YapilyService) {
    suspend operator fun invoke(consentToken: String): Result<List<BankAccount>> =
        runCatching {
            service.getAccounts(consentToken).map { account ->
                BankAccount(
                    id = account.id,
                    name = account.nickname ?: account.accountNames.firstOrNull()?.name ?: account.type ?: "Account",
                    ownerName = account.accountNames.firstOrNull()?.name,
                    iban = account.accountIdentifications.firstOrNull { it.type == "IBAN" }?.identification,
                    balance = account.balance ?: 0.0,
                    currency = account.currency ?: "EUR",
                )
            }
        }
}
