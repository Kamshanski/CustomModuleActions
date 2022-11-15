@file:Suppress("RemoveExplicitTypeArguments")

package dev.itssho.module.qpay.module.name.di

import dev.itssho.module.qpay.module.common.di.UiScopeQ
import dev.itssho.module.qpay.module.name.presentation.NameViewModel
import dev.itssho.module.qpay.module.name.ui.NameUi
import dev.itssho.module.util.koin.parametrizedLocalKoinScopeFactoryOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.swing.Swing
import org.koin.core.module.Module
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module

// TODO разнести модули DI по пакетам
fun makeNameDataModule(commonDataModule: Module): Module = module {
}.apply {
	includes(commonDataModule)
}

fun makeNameFeatureModule(commonFeatureModule: Module, nameDataModule: Module) = module {
	parametrizedLocalKoinScopeFactoryOf(::NameKoinDi) {
		scoped(UiScopeQ) { CoroutineScope(Job() + Dispatchers.Swing) }
		scopedOf(::NameViewModel)
		scoped { NameUi(get(), get(), get<CoroutineScope>(UiScopeQ)) }
	}
}.apply {
	includes(commonFeatureModule, nameDataModule)
}