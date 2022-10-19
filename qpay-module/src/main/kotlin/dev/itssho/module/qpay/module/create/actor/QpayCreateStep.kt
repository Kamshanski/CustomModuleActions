package dev.itssho.module.qpay.module.create.actor

import dev.itssho.module.hierarchy.HierarchyObject

suspend fun QpayCreateStep(structure: HierarchyObject, createDi: QpayCreateDi) {
	createDi.insertStructure(structure)

	val viewModel = createDi.getViewModel()
	val ui = createDi.getUi()

	ui.showForResult()

	viewModel.finish()
	ui.finish()
}