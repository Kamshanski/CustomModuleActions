package dev.itssho.module.shared.dialog.ui

import dev.itssho.module.core.actor.Context
import dev.itssho.module.core.actor.requireContext
import dev.itssho.module.core.ui.UiPlatform
import dev.itssho.module.shared.dialog.ui.idea.IdeaYesNoDialogConstructor
import dev.itssho.module.shared.dialog.ui.swing.SwingYesNoDialogConstructor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.swing.Swing
import kotlinx.coroutines.withContext
import javax.swing.JComponent

fun YesNoPlatformDialog(
	title: String,
	context: Context,
	modal: Boolean = false,
	canBeParent: Boolean = false,
	width: Int = 500,
	height: Int = 300,
	okListener: () -> Unit = {},
	cancelListener: () -> Unit = {},
	windowCloseListener: () -> Unit = {},	// TODO добавить слушателя на кнопку крестика
	dialogContent: () -> JComponent,
) {
	val dialog = when (val platform = context.platform) {
		is UiPlatform.JET_BRAINS -> {
			val jbContext = platform.requireContext(context)
			IdeaYesNoDialogConstructor(title = title, project = jbContext.ideProject, modal = modal, canBeParent = canBeParent, width = width, height = height, dialogContent = dialogContent)
		}
		is UiPlatform.SWING      -> {
			val swingContext = platform.requireContext(context)
			SwingYesNoDialogConstructor(title = title, frame = swingContext.frame, modal = modal, width = width, height = height, dialogContent = dialogContent)
		}
	}

	dialog.addOnWindowCloseAction { windowCloseListener() }
	dialog.addOnCancelAction { cancelListener() }
	dialog.addOnOkAction {  okListener() }

	dialog
		.build()
		.showUp()
}