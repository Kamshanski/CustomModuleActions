package dev.itssho.module.service.preferences.domain.usecase

import dev.itssho.module.service.preferences.data.persistent.Settings
import dev.itssho.module.service.preferences.domain.repository.SettingsRepository

class GetSettingsUseCase(private val settingsRepository: SettingsRepository) {

	operator fun invoke(): Settings =
		settingsRepository.get()
}