package dev.itssho.module.shared.dialog.ui.idea

import com.intellij.CommonBundle
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.DialogWrapper.CANCEL_EXIT_CODE
import dev.itssho.module.core.context.ProjectWindowClickContext
import dev.itssho.module.core.ui.DialogUI
import invokeAll
import kotlinx.coroutines.CoroutineScope
import swing.removeAllActionListeners
import java.util.concurrent.atomic.AtomicBoolean
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.SwingUtilities

abstract class YesNoIdeaDialog<T>(
	val context: ProjectWindowClickContext,
	scope: CoroutineScope,
	canBeParent: Boolean = false,
	applicationModalIfPossible: Boolean = true,
) : DialogUI<T>(scope) {

	private val dialogWrapper by lazy {
		DummyDialogWrapper(context.ideProject, canBeParent, applicationModalIfPossible)
	}

	private val okActions: MutableList<() -> Unit> = mutableListOf()
	private val cancelActions: MutableList<() -> Unit> = mutableListOf()
	private val windowCloseActions: MutableList<() -> Unit> = mutableListOf()

	final override fun initialize() {
		val component = constructCenterView()
		dialogWrapper.initializeWithCenterComponent(component)
		configureDialog(dialogWrapper)
	}

	abstract fun constructCenterView(): JComponent

	abstract fun configureDialog(dialogWrapper: DummyDialogWrapper)

	// TODO выделить метод для запуска методов ВьюМодели

	override fun onShow() {
		dialogWrapper.okButton.addActionListener { okActions.invokeAll() }
		dialogWrapper.cancelButton.addActionListener { cancelActions.invokeAll() }

		dialogWrapper.pack()

		dialogWrapper.showAndGet()
	}

	override fun onFinish() {
		super.onFinish()
		dialogWrapper.close(CANCEL_EXIT_CODE)
	}

	class DummyDialogWrapper(
		project: Project,
		canBeParent: Boolean,
		applicationModalIfPossible: Boolean,
	) : DialogWrapper(project, canBeParent, applicationModalIfPossible) {

		private val initialized = AtomicBoolean(false)
		private var doCreateCenterPanel: (() -> JComponent)? = null

		init {
			this.title = title
		}

		fun initializeWithCenterComponent(centerPanel: JComponent) {
			if (!initialized.getAndSet(true)) {
				doCreateCenterPanel = { centerPanel }
				init()
				okButton.removeAllActionListeners()
				cancelButton.removeAllActionListeners()

				okButton.text = CommonBundle.getOkButtonText()
				cancelButton.text = CommonBundle.getCancelButtonText()
			}
		}

		override fun createCenterPanel(): JComponent {
			return doCreateCenterPanel?.invoke()
				?: throw IllegalStateException("Cannot createCenterPanel as DummyDialogWrapper was not initialized with initializeWithCenterComponent")
		}

		val okButton: JButton get() = getButton(okAction)!!
		val cancelButton: JButton get() = getButton(cancelAction)!!

		public override fun doOKAction() {
			SwingUtilities.invokeLater {
				super.doOKAction()
			}
		}
	}

	fun resumeDialogWithOkAction(result: T) {
		setDialogResult(result)
		dialogWrapper.doOKAction()
	}

	fun resumeDialogWithCancelAction(result: T) {
		setDialogResult(result)
		dialogWrapper.doCancelAction()
	}


	/** Use set in [asyncOnUIThread] or in [SwingUtilities.invokeLater] */
	override var title: String
		get() = dialogWrapper.title
		set(value) {
			dialogWrapper.title = value
		}
	/** Use set in [asyncOnUIThread] or in [SwingUtilities.invokeLater] */
	override var width: Int
		get() = dialogWrapper.size.width
		set(value) {
			dialogWrapper.setSize(value, height)
		}
	/** Use set in [asyncOnUIThread] or in [SwingUtilities.invokeLater] */
	override var height: Int
		get() = dialogWrapper.size.height
		set(value) {
			dialogWrapper.setSize(width, value)
		}

	override fun addOkListener(listener: () -> Unit) {
		okActions.add(0, listener)
	}

	override fun addCancelListener(listener: () -> Unit) {
		cancelActions.add(0, listener)
	}

	override fun addWindowCloseListener(listener: () -> Unit) {
		windowCloseActions.add(0, listener)
	}

	/** Изменения диалога нужно делать тут */
	fun asyncOnUIThread(uiChanges: () -> Unit) {
		SwingUtilities.invokeLater(uiChanges)
	}
}