package dev.itssho.module.qpay.module.common.data.repository

import dev.itssho.module.qpay.module.common.data.datasource.SettingsDataSource
import dev.itssho.module.qpay.module.common.domain.repository.SettingsRepository
import dev.itssho.module.shared.preferences.Settings

class SettingsRepositoryImpl(private val settingsDataSource: SettingsDataSource): SettingsRepository {

	override fun get(): Settings =
		settingsDataSource.getSettings()
}