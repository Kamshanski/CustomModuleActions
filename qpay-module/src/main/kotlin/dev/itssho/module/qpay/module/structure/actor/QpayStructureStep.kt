package dev.itssho.module.qpay.module.structure.actor

import dev.itssho.module.qpay.module.structure.di.QpayStructureDi
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureStepResult

suspend fun QpayStructureStep(di: QpayStructureDi): QpayStructureStepResult {
	val viewModel = di.getViewModel()
	val ui = di.getUi()

	val result = ui.showForResult()

	viewModel.finish()
	ui.finish()

	return result
}