package dev.itssho.module.service.preferences.data.datasource

import dev.itssho.module.service.preferences.data.persistent.ModuleCreatorSettingsState
import dev.itssho.module.service.preferences.data.persistent.Settings

class SettingsDataSource {

	fun getSettings(): Settings {
		val settings = ModuleCreatorSettingsState.getInstance()
		return settings
	}
}