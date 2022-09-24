package dev.itssho.module.qpay.module.actor.di

import dev.itssho.module.qpay.module.actor.di.component.QpayStructureKoinDi
import dev.itssho.module.qpay.module.common.domain.usecase.GetModuleNameUseCase
import dev.itssho.module.qpay.module.common.domain.usecase.GetTextUseCase
import dev.itssho.module.qpay.module.structure.actor.di.NavScopeQ
import dev.itssho.module.qpay.module.structure.actor.di.UiScopeQ
import dev.itssho.module.qpay.module.structure.domain.GenerateUniqueIdUseCase
import dev.itssho.module.qpay.module.structure.domain.GetProjectHierarchyUseCase
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureViewModel
import dev.itssho.module.qpay.module.structure.ui.QpayStructureUi
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelUi
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelViewModel
import dev.itssho.module.util.koin.factoryScopeOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.swing.Swing
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module

fun makeStructureDataModule() = module {
	factoryOf(::GetProjectHierarchyUseCase)
	factoryOf(::GenerateUniqueIdUseCase)
	factoryOf(::GetModuleNameUseCase)
	factoryOf(::GetTextUseCase)
}

fun makeStructureModule() = module {
	factoryScopeOf(::QpayStructureKoinDi) {
		scoped(UiScopeQ) { CoroutineScope(Job() + Dispatchers.Swing) }
		scoped(NavScopeQ) { CoroutineScope(Job() + Dispatchers.Default) }

		scopedOf(::TreePanelViewModel)
		scoped { TreePanelUi(get(), get(UiScopeQ)) }
		scopedOf(::QpayStructureViewModel)
		scoped { QpayStructureUi(get(), get(), get(), get(UiScopeQ)) }
	}
}