package dev.itssho.module.qpay.module.name.actor

import dev.itssho.module.qpay.module.name.presentation.model.NameStepResult

suspend fun NameStep(di: NameDi): NameStepResult {
	val viewModel = di.getViewModel()
	val ui = di.getUi()

	val result = ui.showForResult()

	ui.finish()
	viewModel.finish()

	return result
}