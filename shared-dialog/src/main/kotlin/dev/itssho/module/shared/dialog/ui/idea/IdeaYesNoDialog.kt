package dev.itssho.module.shared.dialog.ui.idea

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import dev.itssho.module.shared.dialog.ui.YesNoDialog
import invokeAll
import javax.swing.JButton
import javax.swing.JComponent

class IdeaYesNoDialog(
	val project: Project,
	title: String,
	private val manualWidth: Int,
	private val manualHeight: Int,
	private val okActions: List<() -> Unit>,
	private val cancelActions: List<() -> Unit>,
	private val windowCloseActions: List<() -> Unit>, // TODO (dispose не подходит. Он вызывается после клика по Ок)
	canBeParent: Boolean,
	applicationModalIfPossible: Boolean,
	private val createCenterComponent: () -> JComponent?,
) : DialogWrapper(project, canBeParent, applicationModalIfPossible), YesNoDialog {

	init {
		init()
		this.title = title
		setSize(manualWidth, manualHeight)
	}

	override fun createCenterPanel(): JComponent? = createCenterComponent()

	override val okButton: JButton?
		get() = getButton(okAction)
	override val cancelButton: JButton?
		get() = getButton(cancelAction)

	override fun showUp() {
		setupDialogButtonListeners()
		setSize(manualWidth, manualHeight)
		show()
	}


	private fun setupDialogButtonListeners() {
		okButton!!.addActionListener { okActions.invokeAll() }
		cancelButton!!.addActionListener { cancelActions.invokeAll() }
	}

	override fun close() {
		dispose()
	}
}