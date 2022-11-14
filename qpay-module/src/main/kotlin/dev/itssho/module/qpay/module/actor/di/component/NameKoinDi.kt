package dev.itssho.module.qpay.module.actor.di.component

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.name.actor.NameDi
import dev.itssho.module.qpay.module.name.presentation.NameViewModel
import dev.itssho.module.qpay.module.name.ui.NameUi
import dev.itssho.module.qpay.module.structure.actor.di.UiScopeQ
import dev.itssho.module.util.koin.LocalKoinScope
import kotlinx.coroutines.CoroutineScope
import org.koin.core.Koin

class NameKoinDi(koin: Koin) : LocalKoinScope(koin), NameDi {

	override fun getUi(): NameUi = scope.get()

	override fun getViewModel(): NameViewModel = scope.get()

	override fun getUiScope() = scope.get<CoroutineScope>(UiScopeQ)

	override fun insertValueStorage(valueStorage: FullyEditableValueStorage) {
		scope.declare(valueStorage, secondaryTypes = listOf(ValueStorage::class, MutableValueStorage::class))
	}

	override fun insertModuleAction(moduleAction: ModuleAction) {
		scope.declare(moduleAction)
	}
}