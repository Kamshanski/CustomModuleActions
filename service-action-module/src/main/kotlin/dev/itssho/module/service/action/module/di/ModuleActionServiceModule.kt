@file:Suppress("RemoveExplicitTypeArguments")

package dev.itssho.module.service.action.module.di

import dev.itssho.module.core.context.JBContext
import dev.itssho.module.service.action.module.ModuleActionService
import dev.itssho.module.service.action.module.data.datasource.ModuleActionDataSource
import dev.itssho.module.service.action.module.data.repository.ScriptRepositoryImpl
import dev.itssho.module.service.action.module.domain.repository.ScriptRepository
import dev.itssho.module.service.action.module.domain.usecase.GetScriptsUseCase
import dev.itssho.module.service.action.module.domain.usecase.UpdateScriptsUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private fun makeModuleActionServiceDataModule(rootModule: Module) = module {
	factory { ModuleActionService.getInstance(get<JBContext>().ideProject) }

	singleOf(::ModuleActionDataSource)
	singleOf(::ScriptRepositoryImpl) bind ScriptRepository::class
}.apply {
	includes(rootModule)
}

private fun makeModuleActionServiceDomainModule(dataModule: Module, sharedFileDomainModule: Module, preferencesServiceDomainModule: Module) = module {
	factoryOf(::UpdateScriptsUseCase)
	factoryOf(::GetScriptsUseCase)
}.apply {
	includes(dataModule)
	includes(sharedFileDomainModule)
	includes(preferencesServiceDomainModule)
}

fun makeModuleActionServiceModule(rootModule: Module, sharedFileModule: Module, preferencesServiceModule: Module) = module {
	val dataModule = makeModuleActionServiceDataModule(rootModule)
	val domainModule = makeModuleActionServiceDomainModule(dataModule, sharedFileModule, preferencesServiceModule)

	includes(dataModule, domainModule)
}