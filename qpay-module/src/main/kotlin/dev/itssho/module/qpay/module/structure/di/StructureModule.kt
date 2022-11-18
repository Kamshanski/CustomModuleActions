@file:Suppress("RemoveExplicitTypeArguments")

package dev.itssho.module.qpay.module.structure.di

import dev.itssho.module.qpay.module.common.di.UiScopeQ
import dev.itssho.module.qpay.module.structure.domain.usecase.GenerateUniqueIdUseCase
import dev.itssho.module.qpay.module.structure.domain.usecase.InitializeHierarchyUseCase
import dev.itssho.module.qpay.module.structure.domain.usecase.InterpretHierarchyTextsUseCase
import dev.itssho.module.qpay.module.structure.domain.usecase.ValidateHierarchyUseCase
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureViewModel
import dev.itssho.module.qpay.module.structure.ui.StructureUi
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelUi
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelViewModel
import dev.itssho.module.util.koin.factoryOf
import dev.itssho.module.util.koin.factoryScopeOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.swing.Swing
import org.koin.core.module.Module
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module

private fun makeStructureDataModule(rootModule: Module): Module = module {
}.apply {
	includes(rootModule)
}

private fun makeStructureFeatureModule(dataModule: Module) = module {
	factoryScopeOf(::QpayStructureKoinDi) {
		factoryOf(::GenerateUniqueIdUseCase)
		factoryOf(::InitializeHierarchyUseCase)
		factoryOf(::InterpretHierarchyTextsUseCase)
		factoryOf(::ValidateHierarchyUseCase)

		scoped(UiScopeQ) { CoroutineScope(Job() + Dispatchers.Swing) }

		scopedOf(::TreePanelViewModel)
		scoped { TreePanelUi(get(), get<CoroutineScope>(UiScopeQ)) }
		scopedOf(::QpayStructureViewModel)
		scoped { StructureUi(get(), get(), get(), get(), get<CoroutineScope>(UiScopeQ)) }
	}
}.apply {
	includes(dataModule)
}

fun makeStructureModule(rootModule: Module) = module {
	val dataModule = makeStructureDataModule(rootModule)
	val featureModule = makeStructureFeatureModule(dataModule)

	includes(featureModule)
}