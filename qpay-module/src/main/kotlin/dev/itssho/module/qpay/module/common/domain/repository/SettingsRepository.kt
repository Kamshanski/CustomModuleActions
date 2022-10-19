package dev.itssho.module.qpay.module.common.domain.repository

import dev.itssho.module.shared.preferences.Settings

interface SettingsRepository {

	fun get(): Settings
}