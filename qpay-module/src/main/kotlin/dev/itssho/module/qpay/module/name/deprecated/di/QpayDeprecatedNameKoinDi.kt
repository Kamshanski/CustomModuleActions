package dev.itssho.module.qpay.module.name.deprecated.di

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.qpay.module.common.di.UiScopeQ
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.name.deprecated.presentation.QpayNameViewModel
import dev.itssho.module.qpay.module.name.deprecated.ui.NameUI
import dev.itssho.module.util.koin.LocalKoinScope
import kotlinx.coroutines.CoroutineScope
import org.koin.core.Koin

class QpayDeprecatedNameKoinDi(koin: Koin) : LocalKoinScope(koin), QpayNameDi {

	companion object {

		fun Koin.getQpayNameKoinDi(
			valueStorage: FullyEditableValueStorage,
			moduleAction: ModuleAction,
		): QpayDeprecatedNameKoinDi =
			get<QpayDeprecatedNameKoinDi>().apply {
				scope.declare(valueStorage, secondaryTypes = listOf(ValueStorage::class, MutableValueStorage::class))
				scope.declare(moduleAction)
			}
	}

	init {
	}

	override fun getUi(): NameUI = scope.get()

	override fun getViewModel(): QpayNameViewModel = scope.get()

	override fun getUiScope() = scope.get<CoroutineScope>(UiScopeQ)
}