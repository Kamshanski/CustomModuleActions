package ui

import common.runTestFrame
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.common.domain.usecase.SetModuleNameUseCase
import dev.itssho.module.qpay.module.name.presentation.QpayNameViewModel
import dev.itssho.module.qpay.module.name.ui.QpayNameUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.swing.Swing

private val setModuleNameUseCase = SetModuleNameUseCase(FullyEditableValueStorage())

fun main() = runTestFrame {
	val uiScope = CoroutineScope(Job() + Dispatchers.Swing)

	val vm = QpayNameViewModel(setModuleNameUseCase)
	QpayNameUi(scope = uiScope, viewModel = vm).constructView()
}