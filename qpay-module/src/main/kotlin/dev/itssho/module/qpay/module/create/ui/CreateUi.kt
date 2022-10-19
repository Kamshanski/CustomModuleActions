package dev.itssho.module.qpay.module.create.ui

import coroutine.collectOn
import coroutine.observe
import dev.itssho.module.component.Scroll
import dev.itssho.module.component.resources.Strings
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.qpay.module.create.presentation.QpayCreateViewModel
import dev.itssho.module.shared.dialog.ui.idea.YesNoIdeaDialog
import dev.itssho.module.ui.util.constructor.jProgressBar
import dev.itssho.module.ui.util.constructor.jbTextArea
import dev.itssho.module.ui.util.constructor.jiLabel
import dev.itssho.module.ui.util.constructor.table
import kotlinx.coroutines.CoroutineScope
import java.awt.event.ActionListener
import javax.swing.JComponent
import javax.swing.SwingUtilities

class CreateUi(
	context: JBContext,
	private val viewModel: QpayCreateViewModel,
	scope: CoroutineScope,
) : YesNoIdeaDialog<Unit>(context = context, scope = scope) {

	override fun constructCenterView(): JComponent {
		return table {
			addCell(stateLabel).left().expandX().fillX()
			row()

			addCell(processLabel).left().expandX().fillX()
			row()

			addCell(progressBar).expandX().fillX()
			row()

			addCell(Scroll { log }).expand().fill()
		}
	}

	override fun configureDialog(dialogWrapper: DummyDialogWrapper) {
		ActionListener { viewModel.close() }.let {
			dialogWrapper.okButton.addActionListener(it)
			dialogWrapper.cancelButton.addActionListener(it)
		}

		initViewModelObservers()

		// TODO Эта штука внутри вызывает launch, хотя не должна. Отсюда сообщения идеи, что операции проходят в не том потоке.
		SwingUtilities.invokeLater {
			viewModel.startModuleCreation()
		}

		title = Strings.Create.title
		width = 500
		height = 500
	}

	private fun initViewModelObservers() {
		viewModel.finalResult.observe(scope) { result ->
			result ?: return@observe
			resumeDialogWithOkAction(result)
		}

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

	private val stateLabel = jiLabel()
	private val processLabel = jiLabel()
	private val progressBar = jProgressBar()
	private val log = jbTextArea(editable = false)

	private val logDocument = log.document
}