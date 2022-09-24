package dev.itssho.module.qpay.module.create.actor

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.qpay.module.create.presentation.QpayCreateViewModel
import dev.itssho.module.qpay.module.create.ui.QpayCreateUi
import kotlinx.coroutines.CoroutineScope

interface QpayCreateDi {

	fun getUi(): QpayCreateUi

	fun getViewModel(): QpayCreateViewModel

	fun getUiScope(): CoroutineScope

	fun insertStructure(structure: HierarchyObject)
}