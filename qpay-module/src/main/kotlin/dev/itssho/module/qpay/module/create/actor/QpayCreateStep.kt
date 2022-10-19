package dev.itssho.module.qpay.module.create.actor

import dev.itssho.module.hierarchy.HierarchyObject

suspend fun QpayCreateStep(moduleName: String, structure: HierarchyObject, createDi: QpayCreateDi) {
	createDi.insertModuleName(moduleName)
	createDi.insertStructure(structure)

	val viewModel = createDi.getViewModel()
	val ui = createDi.getUi()

	ui.showForResult()

	viewModel.finish()
	ui.finish()
}