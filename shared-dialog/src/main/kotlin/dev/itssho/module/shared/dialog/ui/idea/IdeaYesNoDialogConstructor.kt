package dev.itssho.module.shared.dialog.ui.idea

import com.intellij.openapi.project.Project
import dev.itssho.module.shared.dialog.ui.YesNoDialogConstructor
import javax.swing.JComponent

class IdeaYesNoDialogConstructor(
	title: String,
	val project: Project,
	modal: Boolean = false,
	private val canBeParent: Boolean = false,
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

	override fun build() =
		IdeaYesNoDialog(
			title = title,
			okActions = okActions,
			cancelActions = cancelActions,
			windowCloseActions = windowCloseActions,
			project = project,
			manualWidth = width,
			manualHeight = height,
			canBeParent = canBeParent,
			applicationModalIfPossible = modal
		) {
			dialogContent()
		}
}