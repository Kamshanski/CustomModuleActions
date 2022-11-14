package dev.itssho.module.qpay.module.name.actor

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.name.presentation.model.NameStepResult

suspend fun NameStep(valueStorage: FullyEditableValueStorage, moduleAction: ModuleAction, di: NameDi): NameStepResult {
	di.insertValueStorage(valueStorage)
	di.insertModuleAction(moduleAction)

	val viewModel = di.getViewModel()
	val ui = di.getUi()

	val result = ui.showForResult()

	ui.finish()
	viewModel.finish()

	return result
}