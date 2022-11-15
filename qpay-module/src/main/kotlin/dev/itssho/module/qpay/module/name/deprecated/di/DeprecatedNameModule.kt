@file:Suppress("RemoveExplicitTypeArguments")

package dev.itssho.module.qpay.module.name.deprecated.di

import dev.itssho.module.qpay.module.name.deprecated.presentation.QpayNameViewModel
import dev.itssho.module.qpay.module.name.deprecated.ui.NameUI
import dev.itssho.module.qpay.module.structure.di.UiScopeQ
import dev.itssho.module.util.koin.parametrizedLocalKoinScopeFactoryOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.swing.Swing
import org.koin.core.module.Module
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module

fun makeDeprecatedNameDataModule(commonDataModule: Module) = module {
}.apply {
	includes(commonDataModule)
}

fun makeDeprecatedNameFeatureModule(commonFeatureModule: Module, deprecatedNameDataModule: Module) = module {
	parametrizedLocalKoinScopeFactoryOf(::QpayDeprecatedNameKoinDi) {
		scoped(UiScopeQ) { CoroutineScope(Job() + Dispatchers.Swing) }
		scopedOf(::QpayNameViewModel)
		scoped {
			NameUI(get(), get(), get<CoroutineScope>(UiScopeQ))
		}
	}
}.apply {
	includes(commonFeatureModule)
	includes(deprecatedNameDataModule)
}