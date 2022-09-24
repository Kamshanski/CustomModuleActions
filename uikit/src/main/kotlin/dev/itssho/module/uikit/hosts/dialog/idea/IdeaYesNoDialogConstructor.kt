package dev.itssho.module.uikit.hosts.dialog.idea

import com.intellij.openapi.project.Project
import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.dsl.panel.panel
import dev.itssho.module.uikit.hosts.dialog.YesNoDialogConstructor

class IdeaYesNoDialogConstructor(
    title: String,
    val project: Project,
    modal: Boolean = false,
    private val canBeParent: Boolean = false,
    private val width: Int = 500,
    private val height: Int = 300,
    dialogContent: JXLinearPanel.() -> Unit,
) : YesNoDialogConstructor(title, modal, dialogContent) {

    private val cancelActions: MutableList<() -> Unit> = mutableListOf()
    private val okActions: MutableList<() -> Unit> = mutableListOf()

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
            project = project,
            manualWidth = width,
            manualHeight = height,
            canBeParent = canBeParent,
            applicationModalIfPossible = modal
        ) {
            panel { dialogContent() }
        }
}