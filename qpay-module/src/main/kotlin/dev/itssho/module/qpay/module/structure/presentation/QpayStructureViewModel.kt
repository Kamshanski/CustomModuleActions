package dev.itssho.module.qpay.module.structure.presentation

import dev.itssho.module.core.presentation.ViewModel
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelState
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.swing.Swing

class QpayStructureViewModel(
	private val treePanelViewModel: TreePanelViewModel,
) : ViewModel() {

	private val _navAction = MutableSharedFlow<QpayStructureNavAction>(0)
	val navAction: Flow<QpayStructureNavAction> = _navAction

	fun proceed() {
		// TODO сделать проверки. При ошибках не закрывать экран
		dispatchedLaunch(Dispatchers.Default) {
			val state = treePanelViewModel.state.value
			val structure = (state as? TreePanelState.Content)?.structure ?: throw IllegalStateException("Structure is not ready yet")
			_navAction.emit(QpayStructureNavAction.Continue(structure))
		}
	}

	fun close() {
		dispatchedLaunch(Dispatchers.Default) {
			_navAction.emit(QpayStructureNavAction.Close)
		}
	}
}
