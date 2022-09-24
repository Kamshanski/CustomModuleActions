package dev.itssho.module.qpay.module.structure.ui

import dev.itssho.module.component.Scroll
import dev.itssho.module.component.table.MSColumn
import dev.itssho.module.component.table.MSRow
import dev.itssho.module.core.ui.UserInterface
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureViewModel
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelUi
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelViewModel
import kotlinx.coroutines.CoroutineScope
import javax.swing.JButton
import javax.swing.JComponent

class QpayStructureUi(
	private val viewModel: QpayStructureViewModel,
	private val treePanelViewModel: TreePanelViewModel,
	private val treePanelUi: TreePanelUi,
	scope: CoroutineScope,
) : UserInterface(scope) {

	override fun constructView(): JComponent {
		val layout = composeLayout()
		initViewModelObserve()
		return layout
	}

	private fun composeLayout(): JComponent {
		val layout = MSRow(
			Scroll {
				treePanelUi.constructView()
			},
			MSColumn(
				JButton("QII"),
				JButton("GUAN DONG"),
				JButton("WU"),
			),
		).buildPane()

		return layout
	}

	private fun initViewModelObserve() {
		treePanelUi.initViewModelObserve()
	}

	override fun finish() {
		viewModel.finish()
	}
}
