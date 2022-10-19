package dev.itssho.module.qpay.module.common.data.datasource

import dev.itssho.module.shared.preferences.ModuleCreatorSettingsState
import dev.itssho.module.shared.preferences.Settings

class SettingsDataSource {

	fun getSettings(): Settings {
		val settings = ModuleCreatorSettingsState.getInstance()
		return settings
	}
}