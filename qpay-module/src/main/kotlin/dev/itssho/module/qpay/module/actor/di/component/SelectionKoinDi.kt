package dev.itssho.module.qpay.module.actor.di.component

import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.selection.actor.SelectionDi
import dev.itssho.module.qpay.module.selection.presentation.SelectionViewModel
import dev.itssho.module.qpay.module.selection.ui.SelectionUi
import dev.itssho.module.util.koin.LocalKoinScope
import org.koin.core.Koin

class SelectionKoinDi(koin: Koin) : LocalKoinScope(koin), SelectionDi {

	override fun getUi(): SelectionUi = scope.get()

	override fun getViewModel(): SelectionViewModel = scope.get()

	override fun insertValueStorage(valueStorage: FullyEditableValueStorage) {
		scope.declare(valueStorage, secondaryTypes = listOf(ValueStorage::class, MutableValueStorage::class))
	}
}