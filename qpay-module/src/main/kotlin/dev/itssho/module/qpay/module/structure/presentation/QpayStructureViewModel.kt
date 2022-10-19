package dev.itssho.module.qpay.module.structure.presentation

import dev.itssho.module.core.presentation.ViewModel
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelState
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QpayStructureViewModel(
	private val treePanelViewModel: TreePanelViewModel,
) : ViewModel() {

	private val _finalResult = MutableStateFlow<QpayStructureStepResult?>(null)
	val finalResult = _finalResult as StateFlow<QpayStructureStepResult?>

	fun proceed() {
		// TODO сделать проверки. При ошибках не закрывать экран
		val state = treePanelViewModel.state.value
		val structure = (state as? TreePanelState.Content)?.structure ?: throw IllegalStateException("Structure is not ready yet")
		_finalResult.value = QpayStructureStepResult.Structure(structure)
	}

	fun close() {
		_finalResult.value = QpayStructureStepResult.Nothing
	}
}
