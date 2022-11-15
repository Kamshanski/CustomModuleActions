package dev.itssho.module.qpay.module.selection.di

import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.selection.presentation.SelectionViewModel
import dev.itssho.module.qpay.module.selection.ui.SelectionUi
import dev.itssho.module.util.koin.LocalKoinScope
import org.koin.core.Koin

class SelectionKoinDi(koin: Koin) : LocalKoinScope(koin), SelectionDi {

	companion object {

		fun Koin.getSelectionKoinDi(valueStorage: FullyEditableValueStorage): SelectionKoinDi =
			get<SelectionKoinDi>().apply {
				scope.declare(valueStorage, secondaryTypes = listOf(ValueStorage::class, MutableValueStorage::class))
			}
	}

	override fun getUi(): SelectionUi = scope.get()

	override fun getViewModel(): SelectionViewModel = scope.get()
}