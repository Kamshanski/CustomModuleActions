package dev.itssho.module.qpay.module.name.deprecated.actor

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.name.deprecated.presentation.QpayNameStepResult

suspend fun QpayDeprecatedNameStep(valueStorage: FullyEditableValueStorage, moduleAction: ModuleAction, di: QpayNameDi): QpayNameStepResult {
	di.insertValueStorage(valueStorage)
	di.insertModuleAction(moduleAction)

	val viewModel = di.getViewModel()
	val ui = di.getUi()

	val result = ui.showForResult()

	viewModel.finish()
	ui.finish()

	return result
}