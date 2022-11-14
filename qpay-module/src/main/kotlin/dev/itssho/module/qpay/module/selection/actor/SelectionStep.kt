package dev.itssho.module.qpay.module.selection.actor

import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.selection.presentation.SelectionStepResult

suspend fun SelectionStep(valueStorage: FullyEditableValueStorage, di: SelectionDi): SelectionStepResult {
	di.insertValueStorage(valueStorage)

	val viewModel = di.getViewModel()
	val ui = di.getUi()

	val result = ui.showForResult()

	ui.finish()
	viewModel.finish()

	return result
}