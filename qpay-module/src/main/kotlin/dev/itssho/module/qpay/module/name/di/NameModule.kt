@file:Suppress("RemoveExplicitTypeArguments")

package dev.itssho.module.qpay.module.name.di

import dev.itssho.module.qpay.module.common.di.UiScopeQ
import dev.itssho.module.qpay.module.name.domain.usecase.GetInitialNameUseCase
import dev.itssho.module.qpay.module.name.domain.usecase.SubmitModuleNameUseCase
import dev.itssho.module.qpay.module.name.domain.usecase.ValidateModuleNameUseCase
import dev.itssho.module.qpay.module.name.presentation.NameViewModel
import dev.itssho.module.qpay.module.name.ui.NameUi
import dev.itssho.module.util.koin.factoryOf
import dev.itssho.module.util.koin.factoryScopeOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.swing.Swing
import org.koin.core.module.Module
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.ScopeDSL
import org.koin.dsl.module

// TODO разнести модули DI по пакетам
private fun makeNameDataModule(): Module = module {
}.apply {
}

private fun makeNameFeatureModule(dataModule: Module, moduleActionServiceModule: Module) = module {
	factoryScopeOf(::NameKoinDi) {
		factoryOf(::ValidateModuleNameUseCase)
		factoryOf(::SubmitModuleNameUseCase)
		factoryOf(::GetInitialNameUseCase)

		scoped(UiScopeQ) { CoroutineScope(Job() + Dispatchers.Swing) }
		scopedOf(::NameViewModel)
		scoped { NameUi(get(), get(), get<CoroutineScope>(UiScopeQ)) }
	}
}.apply {
	includes(dataModule)
	includes(moduleActionServiceModule)
}

fun makeNameModule(moduleActionServiceModule: Module) = module {
	val dataModule = makeNameDataModule()
	val featureModule = makeNameFeatureModule(
		dataModule = dataModule,
		moduleActionServiceModule = moduleActionServiceModule,
	)

	includes(featureModule)
}