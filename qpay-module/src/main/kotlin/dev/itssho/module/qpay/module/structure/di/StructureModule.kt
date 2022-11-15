@file:Suppress("RemoveExplicitTypeArguments")

package dev.itssho.module.qpay.module.structure.di

import dev.itssho.module.qpay.module.common.di.UiScopeQ
import dev.itssho.module.qpay.module.common.domain.usecase.GetTextUseCase
import dev.itssho.module.qpay.module.structure.domain.usecase.GenerateUniqueIdUseCase
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureViewModel
import dev.itssho.module.qpay.module.structure.ui.StructureUi
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelUi
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelViewModel
import dev.itssho.module.util.koin.factoryOf
import dev.itssho.module.util.koin.parametrizedLocalKoinScopeFactoryOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.swing.Swing
import org.koin.core.module.Module
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.ScopeDSL
import org.koin.dsl.module

fun makeStructureDataModule(commonDataModule: Module): Module = module {
}.apply {
	includes(commonDataModule)
}

private fun ScopeDSL.declareSelectionFeatureDomainEntries() {
	factoryOf(::GenerateUniqueIdUseCase)
	factoryOf(::GetTextUseCase)
}

fun makeStructureFeatureModule(commonFeatureModule: Module, structureDataModule: Module) = module {
	parametrizedLocalKoinScopeFactoryOf(::QpayStructureKoinDi) {
		declareSelectionFeatureDomainEntries()

		scoped(UiScopeQ) { CoroutineScope(Job() + Dispatchers.Swing) }

		scopedOf(::TreePanelViewModel)
		scoped { TreePanelUi(get(), get<CoroutineScope>(UiScopeQ)) }
		scopedOf(::QpayStructureViewModel)
		scoped { StructureUi(get(), get(), get(), get(), get<CoroutineScope>(UiScopeQ)) }
	}
}.apply {
	includes(commonFeatureModule, structureDataModule)
}