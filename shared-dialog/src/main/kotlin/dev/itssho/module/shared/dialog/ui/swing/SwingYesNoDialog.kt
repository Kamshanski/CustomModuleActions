package dev.itssho.module.shared.dialog.ui.swing

import dev.itssho.module.shared.dialog.ui.YesNoDialog
import dev.itssho.module.ui.util.constructor.jButton
import dev.itssho.module.ui.util.constructor.table
import invokeAll
import java.awt.Frame
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JDialog

class SwingYesNoDialog(
	title: String,
	frame: Frame,
	modal: Boolean,
	private val manualWidth: Int,
	private val manualHeight: Int,
	private val cancelActions: List<() -> Unit>,
	private val okActions: List<() -> Unit>,
	private val windowCloseActions: List<() -> Unit>,	// TODO
	dialogContent: () -> JComponent,
) : JDialog(frame, modal), YesNoDialog {

	override val okButton: JButton = jButton(text = "OK") { close() }
	override val cancelButton: JButton = jButton(text = "Cancel") { close() }

	private val contentPanel: JComponent

	init {
		contentPanel = table {
			addCell(dialogContent()).colspan(2).expand().fill()
			row()

			addCell(okButton).right().expandX()
			addCell(cancelButton)
		}

		setTitle(title)
		add(contentPanel)
	}

	override fun showUp() {
		setSize(manualWidth, manualHeight)
		setupDialogButtonListeners()
		isVisible = true
	}

	private fun setupDialogButtonListeners() {
		okButton.addActionListener { okActions.invokeAll() }
		cancelButton.addActionListener { cancelActions.invokeAll() }
	}

	override fun close() {
		dispose()
	}
}