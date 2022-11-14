package dev.itssho.module.qpay.module.create.actor

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage

suspend fun QpayCreateStep(moduleName: String, moduleAction: ModuleAction, valueStorage: FullyEditableValueStorage, structure: HierarchyObject, createDi: QpayCreateDi) {
	createDi.insertModuleName(moduleName)
	createDi.insertModuleAction(moduleAction)
	createDi.insertValueStorage(valueStorage)
	createDi.insertStructure(structure)

	val viewModel = createDi.getViewModel()
	val ui = createDi.getUi()

	ui.showForResult()

	viewModel.finish()
	ui.finish()
}