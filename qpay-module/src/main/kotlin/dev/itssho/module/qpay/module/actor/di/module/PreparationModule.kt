@file:Suppress("RemoveExplicitTypeArguments")

package dev.itssho.module.qpay.module.actor.di.module

import dev.itssho.module.qpay.module.actor.di.component.QpayPreparationKoinDi
import dev.itssho.module.qpay.module.preparation.presentation.PreparationViewModel
import dev.itssho.module.qpay.module.preparation.ui.PreparationUi
import dev.itssho.module.qpay.module.structure.actor.di.DataScopeQ
import dev.itssho.module.qpay.module.structure.actor.di.UiScopeQ
import dev.itssho.module.util.koin.factoryScopeOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.swing.Swing
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module

fun makePreparationDataModule() = module {}

fun makePreparationModule() = module {
	factoryScopeOf(::QpayPreparationKoinDi) {
		scoped(UiScopeQ) { CoroutineScope(Job() + Dispatchers.Swing) }
		scopedOf(::PreparationViewModel)
		scoped { PreparationUi(get(), get(), get<CoroutineScope>(DataScopeQ)) }
	}
}