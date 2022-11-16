package dev.itssho.module.qpay.module.common.di

import com.intellij.ide.script.IdeScriptEngineManager
import dev.itssho.module.component.scripting.idea.IdeaKtsScriptRunnerFactory
import dev.itssho.module.qpay.module.common.data.datasource.OldModuleActionDataSource
import dev.itssho.module.qpay.module.common.data.datasource.SettingsDataSource
import dev.itssho.module.qpay.module.common.data.repository.ModuleActionRepositoryImpl
import dev.itssho.module.qpay.module.common.data.repository.SettingsRepositoryImpl
import dev.itssho.module.qpay.module.common.domain.repository.ModuleActionRepository
import dev.itssho.module.qpay.module.common.domain.repository.SettingsRepository
import dev.itssho.module.qpay.module.common.domain.usecase.GetModuleActionUseCase
import dev.itssho.module.qpay.module.common.domain.usecase.GetSettingsUseCase
import dev.itssho.module.qpay.module.common.domain.usecase.LoadModuleActionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun makeCommonDataModule(rootModule: Module, sharedFileModule: Module): Module =
	module {
		single(DataScopeQ) { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
		factory { IdeScriptEngineManager.getInstance() }

		singleOf(::IdeaKtsScriptRunnerFactory)

		singleOf(::OldModuleActionDataSource)
		singleOf(::SettingsDataSource)

		singleOf(::ModuleActionRepositoryImpl) bind ModuleActionRepository::class
		singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class
	}.apply {
		includes(rootModule)
		includes(sharedFileModule)
	}

fun Module.declareCommonDomainEntities() {
	factoryOf(::GetModuleActionUseCase)
	factoryOf(::LoadModuleActionUseCase)
	factoryOf(::GetSettingsUseCase)
}

fun makeCommonFeatureModule(commonDataModule: Module, sharedFileFeatureModule: Module) =
	module {
		declareCommonDomainEntities()
	}.apply {
		includes(commonDataModule)
		includes(sharedFileFeatureModule)
	}
