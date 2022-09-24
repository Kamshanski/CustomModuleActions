package polygon

import common.runTestFrame
import dev.itssho.module.uikit2.component.text.XLabel
import dev.itssho.module.uikit2.dsl.button
import dev.itssho.module.uikit2.dsl.label
import dev.itssho.module.uikit2.dsl.column
import dev.itssho.module.uikit2.dsl.panel
import dev.itssho.module.uikit2.dsl.row
import dev.itssho.module.uikit2.dsl.text.textField
import dev.itssho.module.uikit2.modify.Modifier
import dev.itssho.module.uikit2.util.swing.addTo
import javax.swing.JScrollPane

fun main() = runTestFrame {

    // TODO Попробоать лэйаут с этим мэнеджером. Он вроде норм ложится на дсл. https://ipsoftware.ru/posts/gridbaglayout/

    panel {
        JScrollPane().also {
            it.setViewportView(
                row {
                    val column = column {
                        label(text = randomText())
                    }
                    button(text = "Add Label") {
                        column.textField(text = "")
                    }
                    button(text = "Add 2 lines Label") {
                        column.add(XLabel(randomText() + "!\n!" + randomText()))
                    }
                }
            )
        }.addTo(this, Modifier)
//                (JTextArea(QpayStruct.toString())).addTo(this)
    }

}