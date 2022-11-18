package dev.itssho.module.service.preferences.di

import dev.itssho.module.service.preferences.data.datasource.SettingsDataSource
import dev.itssho.module.service.preferences.data.repository.SettingsRepositoryImpl
import dev.itssho.module.service.preferences.domain.repository.SettingsRepository
import dev.itssho.module.service.preferences.domain.usecase.GetSettingsUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private fun makeDataPreferencesServiceModule() = module {
	singleOf(::SettingsDataSource)

	singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class
}

private fun makeDomainPreferencesServiceModule(dataModule: Module) = module {
	factoryOf(::GetSettingsUseCase)
}.apply {
	includes(dataModule)
}

fun makePreferencesServiceModule() = module {
	val dataModule = makeDataPreferencesServiceModule()
	val domainModule = makeDomainPreferencesServiceModule(dataModule)

	includes(dataModule, domainModule)
}