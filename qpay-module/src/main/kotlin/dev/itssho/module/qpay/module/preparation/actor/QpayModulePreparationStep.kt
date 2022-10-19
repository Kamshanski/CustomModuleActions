package dev.itssho.module.qpay.module.preparation.actor

import dev.itssho.module.qpay.module.preparation.presentation.PreparationStepResult

suspend fun QpayPreparationStep(di: QpayPreparationDi): PreparationStepResult {
	val viewModel = di.getViewModel()
	val ui = di.getUi()

	val result = ui.showForResult()

	ui.finish()
	viewModel.finish()

	return result
}