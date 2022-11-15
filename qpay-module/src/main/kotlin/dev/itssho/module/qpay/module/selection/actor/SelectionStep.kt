package dev.itssho.module.qpay.module.selection.actor

import dev.itssho.module.qpay.module.selection.di.SelectionDi
import dev.itssho.module.qpay.module.selection.presentation.SelectionStepResult

suspend fun SelectionStep(di: SelectionDi): SelectionStepResult {
	val viewModel = di.getViewModel()
	val ui = di.getUi()

	val result = ui.showForResult()

	viewModel.finish()
	ui.finish()

	return result
}