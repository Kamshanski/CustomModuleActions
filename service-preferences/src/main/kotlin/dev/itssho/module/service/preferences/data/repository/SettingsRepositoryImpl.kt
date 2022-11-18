package dev.itssho.module.service.preferences.data.repository

import dev.itssho.module.service.preferences.data.datasource.SettingsDataSource
import dev.itssho.module.service.preferences.data.persistent.Settings
import dev.itssho.module.service.preferences.domain.repository.SettingsRepository

class SettingsRepositoryImpl(private val settingsDataSource: SettingsDataSource): SettingsRepository {

	override fun get(): Settings =
		settingsDataSource.getSettings()
}