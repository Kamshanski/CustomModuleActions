import common.runTestFrame
import dev.itssho.module.common.component.ktx.scope.ScopeWrapper
import dev.itssho.module.feature.qpay.module.name.ui.QpayModuleNameUi
import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.dsl.button.button
import dev.itssho.module.uikit.dsl.label.label
import dev.itssho.module.uikit.dsl.panel.column
import dev.itssho.module.uikit.dsl.panel.panel
import dev.itssho.module.uikit.dsl.panel.row
import dev.itssho.module.uikit.dsl.select.singleSelectionButtonsGroup
import dev.itssho.module.uikit.dsl.text.textField
import dev.itssho.module.uikit.extensions.addTo
import dev.itssho.module.uikit.hosts.dialog.YesNoDialog
import dev.itssho.module.uikit.hosts.dialog.swing.SwingYesNoDialogConstructor
import dev.itssho.module.uikit.layout.swan.linear.LinearGravity
import dev.itssho.module.uikit.modification.*

var dialog: YesNoDialog? = null

fun main(args: Array<String>) {
    runTestFrame {
        val frame = this
        val scope = ScopeWrapper.newMain()
        val qpayStand = QpayModuleNameUi(scope)


        val panel = panel {
            testLayout()
            button(Modifier.gravity(LinearGravity.END), text = "QPAY MODULE DIALOG") {
                dialog?.close()
                dialog = null

                SwingYesNoDialogConstructor("My presious", frame, isModal = false) {
                    qpayStand.constructView(this)
                }
                    .build()
                    .also { dialog = it }
                    .showUp()
            }
        }
        panel.addTo(frame)
    }
}

fun JXLinearPanel.testLayout() = column(Modifier.wrapHeight()) {
    textField(Modifier.fillMaxHeight(), "hkhlhjkl", isEditable = true)

    row {
        column {
            label(text = "ssdsdsd")
            singleSelectionButtonsGroup {
                addRadioButton(text = "name")
                addRadioButton(text = "fd")
                addRadioButton(text = "fddfdsf")
                addRadioButton(text = "qwqw")
            }
        }
        column {
            label(text = "Domain")
            singleSelectionButtonsGroup {
                addRadioButton(text = "nassdsdsdsme")
                addRadioButton(text = "fd")
                addRadioButton(text = "qwqw")
            }
        }
        column(Modifier.fillMaxWidth()) {
            label(text = "Module Name")
            textField(Modifier.fillMaxWidth(), "kljj")
        }
    }
}