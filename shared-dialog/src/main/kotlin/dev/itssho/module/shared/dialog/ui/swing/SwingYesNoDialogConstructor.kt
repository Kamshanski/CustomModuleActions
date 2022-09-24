package dev.itssho.module.shared.dialog.ui.swing

import dev.itssho.module.shared.dialog.ui.YesNoDialog
import dev.itssho.module.shared.dialog.ui.YesNoDialogConstructor
import javax.swing.JComponent
import javax.swing.JFrame

class SwingYesNoDialogConstructor(
	title: String,
	private val frame: JFrame,
	modal: Boolean = true,
	private val width: Int = 500,
	private val height: Int = 300,
	dialogContent: () -> JComponent,
) : YesNoDialogConstructor(title, modal, dialogContent) {

	private val cancelActions: MutableList<() -> Unit> = mutableListOf()
	private val okActions: MutableList<() -> Unit> = mutableListOf()
	private val windowCloseActions: MutableList<() -> Unit> = mutableListOf()

	override fun addOnWindowCloseAction(windowCloseAction: () -> Unit) {
		windowCloseActions.add(0, windowCloseAction)
	}

	override fun addOnCancelAction(closeAction: () -> Unit) {
		cancelActions.add(0, closeAction)
	}

	override fun addOnOkAction(okAction: () -> Unit) {
		okActions.add(0, okAction)
	}

	override fun build(): YesNoDialog =
		SwingYesNoDialog(
			title = title,
			frame = frame,
			modal = modal,
			manualWidth = width,
			manualHeight = height,
			cancelActions = cancelActions,
			okActions = okActions,
			windowCloseActions = windowCloseActions,
			dialogContent = dialogContent,
		)
}