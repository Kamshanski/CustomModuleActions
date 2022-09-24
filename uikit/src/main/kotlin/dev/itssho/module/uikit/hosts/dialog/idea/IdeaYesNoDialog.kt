package dev.itssho.module.uikit.hosts.dialog.idea

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import dev.itssho.module.uikit.hosts.dialog.YesNoDialog
import dev.itssho.module.uikit.util.invokeAll
import javax.swing.JButton
import javax.swing.JComponent

class IdeaYesNoDialog(
    val project: Project,
    title: String,
    private val manualWidth: Int,
    private val manualHeight: Int,
    private val okActions: List<() -> Unit>,
    private val cancelActions: List<() -> Unit>,
    canBeParent: Boolean,
    applicationModalIfPossible: Boolean,
    private val createCenterComponent: () -> JComponent?,
) : DialogWrapper(project, canBeParent, applicationModalIfPossible), YesNoDialog {

    init {
        this.title = title
        setSize(manualWidth, manualHeight)
        init()
    }

    override fun createCenterPanel(): JComponent? = createCenterComponent()

    override fun getOkButton(): JButton? = getButton(okAction)

    override fun getCancelButton(): JButton? = getButton(cancelAction)


    override fun dispose() {
        cancelActions.forEach { it.invoke() }
        super.dispose()
    }

    override fun showUp() {
        setupDialogButtonListeners()
        defineDialogSize()
        show()
    }

    private fun defineDialogSize() {
        // Расчёт значений для конечных компонентов, чтобы на их основе высчитать размеры JXLinearPanel
        pack()

        val definedWidth = maxOf(contentPanel.minimumSize.width, manualWidth)

        val calculatedHeight = contentPanel.minimumSize.height + preferredSize.height
        val definedHeight = maxOf(calculatedHeight, manualHeight)

        setSize(definedWidth, definedHeight)
    }

    private fun setupDialogButtonListeners() {
        getOkButton()!!.addActionListener { okActions.invokeAll() }
        getCancelButton()!!.addActionListener { cancelActions.invokeAll() }
    }

    override fun close() {
        dispose()
    }
}