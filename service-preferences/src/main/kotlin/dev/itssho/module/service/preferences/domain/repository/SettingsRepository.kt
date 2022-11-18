package dev.itssho.module.service.preferences.domain.repository

import dev.itssho.module.service.preferences.data.persistent.Settings

interface SettingsRepository {

	fun get(): Settings
}