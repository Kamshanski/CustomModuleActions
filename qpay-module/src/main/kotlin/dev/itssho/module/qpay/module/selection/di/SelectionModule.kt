@file:Suppress("RemoveExplicitTypeArguments")

package dev.itssho.module.qpay.module.selection.di

import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.qpay.module.common.di.UiScopeQ
import dev.itssho.module.qpay.module.selection.data.datasource.ModuleActionDataSource
import dev.itssho.module.qpay.module.selection.data.repository.ScriptRepositoryImpl
import dev.itssho.module.qpay.module.selection.domain.repository.ScriptRepository
import dev.itssho.module.qpay.module.selection.domain.usecase.GetScriptsUseCase
import dev.itssho.module.qpay.module.selection.domain.usecase.UpdateScriptsUseCase
import dev.itssho.module.qpay.module.selection.presentation.SelectionViewModel
import dev.itssho.module.qpay.module.selection.ui.SelectionUi
import dev.itssho.module.service.action.module.ModuleActionService
import dev.itssho.module.util.koin.factoryOf
import dev.itssho.module.util.koin.factoryScopeOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.swing.Swing
import org.koin.core.module.Module
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.ScopeDSL
import org.koin.dsl.bind
import org.koin.dsl.module

fun makeSelectionDataModule(commonDataModule: Module) = module {
	// TODO Выделить фабрику для ModuleActionService
	factory { ModuleActionService.getInstance(get<JBContext>().ideProject) }
	singleOf(::ModuleActionDataSource)
	singleOf(::ScriptRepositoryImpl) bind ScriptRepository::class
}.apply {
	includes(commonDataModule)
}

private fun ScopeDSL.declareSelectionFeatureDomainEntries() {
	factoryOf(::UpdateScriptsUseCase)
	factoryOf(::GetScriptsUseCase)
}

fun makeSelectionModule(commonFeatureModule: Module, selectionDataModule: Module) = module {
	factoryScopeOf(::SelectionKoinDi) {
		declareSelectionFeatureDomainEntries()

		scoped(UiScopeQ) { CoroutineScope(Job() + Dispatchers.Swing) }
		scoped { SelectionUi(get(), get(), get<CoroutineScope>(UiScopeQ)) }
		scopedOf(::SelectionViewModel)
	}
}.apply {
	includes(commonFeatureModule, selectionDataModule)
}