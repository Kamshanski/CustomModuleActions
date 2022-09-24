package dev.itssho.module.uikit.hosts.dialog.swing

import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.hosts.dialog.YesNoDialog
import dev.itssho.module.uikit.hosts.dialog.YesNoDialogConstructor
import javax.swing.JFrame

class SwingYesNoDialogConstructor(
    title: String,
    private val frame: JFrame,
    private val isModal: Boolean = true,
    private val width: Int = 500,
    private val height: Int = 300,
    dialogContent: JXLinearPanel.() -> Unit,
) : YesNoDialogConstructor(title, isModal, dialogContent) {

    private val cancelActions: MutableList<() -> Unit> = mutableListOf()
    private val okActions: MutableList<() -> Unit> = mutableListOf()

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
            dialogContent = dialogContent,
        )
}