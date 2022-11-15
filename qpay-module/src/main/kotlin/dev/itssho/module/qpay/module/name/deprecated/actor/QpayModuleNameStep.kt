package dev.itssho.module.qpay.module.name.deprecated.actor

import dev.itssho.module.qpay.module.name.deprecated.di.QpayNameDi
import dev.itssho.module.qpay.module.name.deprecated.presentation.QpayNameStepResult

suspend fun QpayDeprecatedNameStep(di: QpayNameDi): QpayNameStepResult {
	val viewModel = di.getViewModel()
	val ui = di.getUi()

	val result = ui.showForResult()

	viewModel.finish()
	ui.finish()

	return result
}