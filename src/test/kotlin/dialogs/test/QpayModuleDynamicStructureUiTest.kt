import common.runTestFrame
import dev.itssho.module.common.component.ktx.scope.ScopeWrapper
import dev.itssho.module.feature.qpay.module.structure.ui.QpayDynamicStructureUi
import dev.itssho.module.uikit.dsl.panel.panel
import dev.itssho.module.uikit.extensions.addTo
import dev.itssho.module.uikit.hosts.dialog.swing.SwingYesNoDialogConstructor
import javax.swing.JScrollPane

fun main(args: Array<String>) {
    runTestFrame {
        val frame = this
        val scope = ScopeWrapper.newMain()
        val ui = QpayDynamicStructureUi(scope)

        SwingYesNoDialogConstructor("My test", frame, isModal = false) {
            val scrollPane = JScrollPane(panel {
                ui.constructView(this)
            })
            scrollPane.addTo(this)
        }
            .build()
            .showUp()
    }
}