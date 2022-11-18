@file:Suppress("RemoveExplicitTypeArguments")

package dev.itssho.module.qpay.module.selection.di

import dev.itssho.module.qpay.module.common.di.UiScopeQ
import dev.itssho.module.qpay.module.selection.domain.usecase.GetModuleActionByScriptPathUseCase
import dev.itssho.module.qpay.module.selection.domain.usecase.InitializeModuleActionUseCase
import dev.itssho.module.qpay.module.selection.presentation.SelectionViewModel
import dev.itssho.module.qpay.module.selection.ui.SelectionUi
import dev.itssho.module.util.koin.factoryOf
import dev.itssho.module.util.koin.factoryScopeOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.swing.Swing
import org.koin.core.module.Module
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module

private fun makeSelectionFeatureModule(moduleActionServiceModule: Module) = module {
	factoryScopeOf(::SelectionKoinDi) {
		factoryOf(::GetModuleActionByScriptPathUseCase)
		factoryOf(::InitializeModuleActionUseCase)

		scoped(UiScopeQ) { CoroutineScope(Job() + Dispatchers.Swing) }
		scoped { SelectionUi(get(), get(), get<CoroutineScope>(UiScopeQ)) }
		scopedOf(::SelectionViewModel)
	}
}.apply {
	includes(moduleActionServiceModule)
}

fun makeSelectionModule(moduleActionServiceModule: Module) = module {
	val featureModule = makeSelectionFeatureModule(
		moduleActionServiceModule = moduleActionServiceModule,
	)

	includes(featureModule)
}