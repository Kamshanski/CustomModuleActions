package dev.itssho.module.qpay.module.create.actor

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.qpay.module.create.presentation.QpayCreateViewModel
import dev.itssho.module.qpay.module.create.ui.CreateUi
import kotlinx.coroutines.CoroutineScope

interface QpayCreateDi {

	fun getUi(): CreateUi

	fun getViewModel(): QpayCreateViewModel

	fun getUiScope(): CoroutineScope

	fun insertModuleName(moduleName: String)

	fun insertStructure(structure: HierarchyObject)
}