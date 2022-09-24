package ui

import common.runTestFrame
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.common.domain.usecase.GetModuleNameUseCase
import dev.itssho.module.qpay.module.common.domain.usecase.GetTextUseCase
import dev.itssho.module.qpay.module.structure.domain.GenerateUniqueIdUseCase
import dev.itssho.module.qpay.module.structure.domain.GetProjectHierarchyUseCase
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureViewModel
import dev.itssho.module.qpay.module.structure.ui.QpayStructureUi
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelUi
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.swing.Swing


fun main() = runTestFrame {
	val uiScope = CoroutineScope(Job() + Dispatchers.Swing)

	val getProjectHierarchyUseCase = GetProjectHierarchyUseCase()
	val generateUniqueIdUseCase = GenerateUniqueIdUseCase()
	val getModuleNameUseCase = GetModuleNameUseCase(FullyEditableValueStorage())
	val getTextUseCase = GetTextUseCase(getModuleNameUseCase)

	val treePanelViewModel = TreePanelViewModel(getProjectHierarchyUseCase, generateUniqueIdUseCase, getTextUseCase)
	val treePanelUi = TreePanelUi(treePanelViewModel, uiScope)

	val viewModel = QpayStructureViewModel(treePanelViewModel)
	val ui = QpayStructureUi(viewModel, treePanelViewModel, treePanelUi, uiScope)
	ui.constructView()
}