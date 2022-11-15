package dev.itssho.module.qpay.module.create.actor

suspend fun QpayCreateStep(createDi: QpayCreateDi) {
	val viewModel = createDi.getViewModel()
	val ui = createDi.getUi()

	ui.showForResult()

	viewModel.finish()
	ui.finish()
}