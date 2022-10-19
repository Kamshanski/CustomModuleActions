package dev.itssho.module.qpay.module.common.domain.usecase

import dev.itssho.module.qpay.module.common.domain.repository.SettingsRepository
import dev.itssho.module.shared.preferences.Settings
import dev.itssho.module.util.koin.dev.DiRequired

@DiRequired
class GetSettingsUseCase(private val settingsRepository: SettingsRepository) {

	operator fun invoke(): Settings =
		settingsRepository.get()
}