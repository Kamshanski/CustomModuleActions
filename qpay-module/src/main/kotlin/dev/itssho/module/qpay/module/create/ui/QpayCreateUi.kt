package dev.itssho.module.qpay.module.create.ui

import coroutine.collectOn
import coroutine.observe
import dev.itssho.module.component.Scroll
import dev.itssho.module.core.ui.UserInterface
import dev.itssho.module.qpay.module.create.presentation.QpayCreateViewModel
import dev.itssho.module.ui.util.constructor.jProgressBar
import dev.itssho.module.ui.util.constructor.jbTextArea
import dev.itssho.module.ui.util.constructor.jiLabel
import dev.itssho.module.ui.util.constructor.table
import kotlinx.coroutines.CoroutineScope
import javax.swing.JComponent
import javax.swing.SwingUtilities

class QpayCreateUi(
	val viewModel: QpayCreateViewModel,
	scope: CoroutineScope,
) : UserInterface(scope) {

	private val stateLabel = jiLabel()
	private val processLabel = jiLabel()
	private val progressBar = jProgressBar()
	private val log = jbTextArea(editable = false)

	private val logDocument = log.document

	private fun composeLayout() =
		table {
			addCell(stateLabel).left().expandX().fillX()
			row()

			addCell(processLabel).left().expandX().fillX()
			row()

			addCell(progressBar).expandX().fillX()
			row()

			addCell(Scroll { log }).expand().fill()
		}

	private fun initViewModelObserve() {
		viewModel.progress.observe(scope) { progress ->
			progressBar.value = progress.percent

			processLabel.text = progress.itemName ?: ""

			stateLabel.text = progress.itemType?.simpleName ?: ""
		}

		viewModel.lastLog.collectOn(scope) { message ->
			try {
				logDocument.insertString(logDocument.length, message, null)
			} catch (ex: Throwable) {
				println("sad")
			}
		}
	}


	override fun constructView(): JComponent = composeLayout().also {
		initViewModelObserve()
		SwingUtilities.invokeLater {
			viewModel.startModuleCreation()
		}
	}
}