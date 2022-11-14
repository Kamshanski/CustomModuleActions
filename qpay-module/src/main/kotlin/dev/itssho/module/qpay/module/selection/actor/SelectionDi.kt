package dev.itssho.module.qpay.module.selection.actor

import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.selection.presentation.SelectionViewModel
import dev.itssho.module.qpay.module.selection.ui.SelectionUi

interface SelectionDi {

	fun getViewModel(): SelectionViewModel

	fun getUi(): SelectionUi

	fun insertValueStorage(valueStorage: FullyEditableValueStorage)
}