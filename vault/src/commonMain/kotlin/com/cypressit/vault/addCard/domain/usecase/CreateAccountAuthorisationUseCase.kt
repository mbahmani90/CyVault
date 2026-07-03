package com.cypressit.vault.addCard.domain.usecase

import com.cypressit.vault.addCard.data.remote.YapilyService

class CreateAccountAuthorisationUseCase(private val service: YapilyService) {
    // Returns the authorisationUrl to open in Custom Tabs
    suspend operator fun invoke(institutionId: String): Result<String> =
        runCatching {
            service.createAccountAuthorisation(institutionId).data.authorisationUrl
        }
}
