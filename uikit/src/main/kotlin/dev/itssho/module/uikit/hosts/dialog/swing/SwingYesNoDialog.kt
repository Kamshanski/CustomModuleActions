package dev.itssho.module.uikit.hosts.dialog.swing

import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.dsl.button.button
import dev.itssho.module.uikit.modification.Modifier
import dev.itssho.module.uikit.modification.fillMaxHeight
import dev.itssho.module.uikit.modification.gravity
import dev.itssho.module.uikit.dsl.panel.panel
import dev.itssho.module.uikit.dsl.panel.row
import dev.itssho.module.uikit.hosts.dialog.YesNoDialog
import dev.itssho.module.uikit.layout.swan.linear.LinearGravity
import dev.itssho.module.uikit.util.invokeAll
import java.awt.Frame
import javax.swing.JButton
import javax.swing.JDialog

class SwingYesNoDialog(
    title: String,
    frame: Frame,
    modal: Boolean,
    private val manualWidth: Int,
    private val manualHeight: Int,
    private val cancelActions: List<() -> Unit>,
    private val okActions: List<() -> Unit>,
    dialogContent: JXLinearPanel.() -> Unit,
) : JDialog(frame, modal), YesNoDialog {

    // :((((
    private lateinit var _okButton: JButton
    private lateinit var _cancelButton: JButton

    private val contentPanel: JXLinearPanel

    init {
        contentPanel = panel {
            dialogContent()
            row(Modifier.gravity(LinearGravity.BOTTOM).fillMaxHeight()) {
                _okButton = button(text = "OK") { close() }
                _cancelButton = button(text = "Отмена") { close() }
            }
        }

        setTitle(title)
        add(contentPanel)
    }

    override fun getOkButton(): JButton = _okButton

    override fun getCancelButton(): JButton = _cancelButton

    override fun showUp() {
        defineDialogSize()
        setupDialogButtonListeners()
        isVisible = true

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
        _okButton.addActionListener { okActions.invokeAll() }
        _cancelButton.addActionListener { cancelActions.invokeAll() }
    }

    override fun close() {
        dispose()
    }
}