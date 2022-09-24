import common.runTestFrame
import dev.itssho.module.common.component.ktx.scope.ScopeWrapper
import dev.itssho.module.feature.qpay.module.structure.ui.QpayStructureUi
import dev.itssho.module.uikit.hosts.dialog.swing.SwingYesNoDialogConstructor

fun main(args: Array<String>) {
    runTestFrame {
        val frame = this
        val scope = ScopeWrapper.newMain()
        val ui = QpayStructureUi(scope)

        SwingYesNoDialogConstructor("My test", frame, isModal = false) {
            ui.constructView(this)
        }
            .build()
            .showUp()
    }
}