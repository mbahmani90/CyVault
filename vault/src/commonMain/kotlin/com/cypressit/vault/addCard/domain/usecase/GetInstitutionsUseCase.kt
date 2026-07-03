package com.cypressit.vault.addCard.domain.usecase

import com.cypressit.vault.addCard.data.remote.YapilyService
import com.cypressit.vault.addCard.domain.model.Institution

class GetInstitutionsUseCase(private val service: YapilyService) {
    suspend operator fun invoke(query: String, countryCodes: List<String>): Result<List<Institution>> =
        runCatching {
            service.getInstitutions(query, countryCodes).map { institution ->
                Institution(
                    id = institution.id,
                    name = institution.name,
                    bic = null,
                    logo = institution.media.firstOrNull { it.type == "icon" }?.source
                        ?: institution.media.firstOrNull()?.source,
                    countries = institution.countries.map { it.countryCode },
                )
            }
        }
}
