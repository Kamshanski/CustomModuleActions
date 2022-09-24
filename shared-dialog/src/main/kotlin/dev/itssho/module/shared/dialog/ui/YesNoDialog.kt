package dev.itssho.module.shared.dialog.ui

import javax.swing.JButton

interface YesNoDialog {

    val okButton: JButton?

    val cancelButton: JButton?

    fun showUp()

    fun close()
}