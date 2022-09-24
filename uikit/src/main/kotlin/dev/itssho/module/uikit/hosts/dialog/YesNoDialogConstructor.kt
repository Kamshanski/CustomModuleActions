package dev.itssho.module.uikit.hosts.dialog

import dev.itssho.module.uikit.component.panel.JXLinearPanel
import javax.swing.JButton

abstract class YesNoDialogConstructor(
    val title: String = "",
    val modal: Boolean = true,
    protected val dialogContent: JXLinearPanel.() -> Unit,
) {

    abstract fun addOnCancelAction(closeAction: () -> Unit)

    abstract fun addOnOkAction(okAction: () -> Unit)

    abstract fun build(): YesNoDialog
}

interface YesNoDialog {

    fun getOkButton(): JButton?

    fun getCancelButton(): JButton?

    fun showUp()

    fun close()
}