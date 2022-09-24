package dev.itssho.module.shared.dialog.ui

import javax.swing.JComponent

abstract class YesNoDialogConstructor(
    val title: String = "",
    val modal: Boolean = true,
    protected val dialogContent: () -> JComponent,
) {

    abstract fun addOnWindowCloseAction(windowCloseAction: () -> Unit)

    abstract fun addOnCancelAction(closeAction: () -> Unit)

    abstract fun addOnOkAction(okAction: () -> Unit)

    abstract fun build(): YesNoDialog
}