@file:Suppress("RemoveExplicitTypeArguments")

package dev.itssho.module.qpay.module.actor.di.module

import dev.itssho.module.qpay.module.actor.di.component.QpayStructureKoinDi
import dev.itssho.module.qpay.module.common.domain.usecase.GetTextUseCase
import dev.itssho.module.qpay.module.structure.actor.di.UiScopeQ
import dev.itssho.module.qpay.module.structure.domain.usecase.GenerateUniqueIdUseCase
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureViewModel
import dev.itssho.module.qpay.module.structure.ui.StructureUi
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
	factoryOf(::GenerateUniqueIdUseCase)
	factoryOf(::GetTextUseCase)
}

fun makeStructureModule() = module {
	factoryScopeOf(::QpayStructureKoinDi) {
		scoped(UiScopeQ) { CoroutineScope(Job() + Dispatchers.Swing) }

		scopedOf(::TreePanelViewModel)
		scoped { TreePanelUi(get(), get<CoroutineScope>(UiScopeQ)) }
		scopedOf(::QpayStructureViewModel)
		scoped { StructureUi(get(), get(), get(), get(), get<CoroutineScope>(UiScopeQ)) }
	}
}