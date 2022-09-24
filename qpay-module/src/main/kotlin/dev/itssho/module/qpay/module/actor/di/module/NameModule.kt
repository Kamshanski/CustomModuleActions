package dev.itssho.module.qpay.module.actor.di

import dev.itssho.module.qpay.module.actor.di.component.QpayNameKoinDi
import dev.itssho.module.qpay.module.name.presentation.QpayNameViewModel
import dev.itssho.module.qpay.module.name.ui.QpayNameUi
import dev.itssho.module.qpay.module.structure.actor.di.NavScopeQ
import dev.itssho.module.qpay.module.structure.actor.di.UiScopeQ
import dev.itssho.module.util.koin.factoryScopeOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.swing.Swing
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module

fun makeNameDataModule() = module {}

fun makeNameModule() = module {
	factoryScopeOf(::QpayNameKoinDi) {
		scoped(UiScopeQ) { CoroutineScope(Job() + Dispatchers.Swing) }
		scoped(NavScopeQ) { CoroutineScope(Job() + Dispatchers.Default) }
		scopedOf(::QpayNameViewModel)
		scoped { QpayNameUi(get(), get(UiScopeQ)) }
	}
}