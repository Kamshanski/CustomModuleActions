package dev.itssho.module.qpay.module.structure.ui

import coroutine.observe
import dev.itssho.module.component.Scroll
import dev.itssho.module.component.resources.Strings
import dev.itssho.module.component.table.MSColumn
import dev.itssho.module.component.table.MSRow
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureStepResult
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureViewModel
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelUi
import dev.itssho.module.qpay.module.structure.ui.delegate.TreePanelViewModel
import dev.itssho.module.shared.dialog.ui.idea.YesNoIdeaDialog
import kotlinx.coroutines.CoroutineScope
import javax.swing.JButton
import javax.swing.JComponent

class StructureUi(
	context: JBContext,
	private val treePanelViewModel: TreePanelViewModel,
	private val treePanelUi: TreePanelUi,
	private val viewModel: QpayStructureViewModel,
	scope: CoroutineScope,
) : YesNoIdeaDialog<QpayStructureStepResult>(context = context, scope = scope) {

	override fun constructCenterView(): JComponent {
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

	override fun configureDialog(dialogWrapper: DummyDialogWrapper) {
		asyncOnUIThread {
			title = Strings.Structure.title
			width = 500
			height = 500
		}

		dialogWrapper.okButton.addActionListener {
			viewModel.proceed()
		}
		dialogWrapper.cancelButton.addActionListener {
			viewModel.close()
		}

		initViewModelObservers()
	}

	private fun initViewModelObservers() {
		treePanelUi.initViewModelObserve()

		viewModel.finalResult.observe(scope) { result ->
			result ?: return@observe
			when (result) {
				is QpayStructureStepResult.Structure -> resumeDialogWithOkAction(result)
				is QpayStructureStepResult.Nothing   -> resumeDialogWithCancelAction(result)
			}

		}
	}
}
