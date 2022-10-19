package dev.itssho.module.qpay.module.name.actor

import dev.itssho.module.qpay.module.name.presentation.QpayNameStepResult

suspend fun QpayNameStep(di: QpayNameDi): QpayNameStepResult {
	val viewModel = di.getViewModel()
	val ui = di.getUi()

	val result = ui.showForResult()

	ui.finish()
	viewModel.finish()

	return result
}