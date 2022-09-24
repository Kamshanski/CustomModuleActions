package polygon

import common.runTestFrame
import dev.itssho.module.uikit.component.text.JXLabel
import dev.itssho.module.uikit.dsl.button.button
import dev.itssho.module.uikit.dsl.label.label
import dev.itssho.module.uikit.dsl.panel.column
import dev.itssho.module.uikit.dsl.panel.panel
import dev.itssho.module.uikit.dsl.panel.row
import dev.itssho.module.uikit.extensions.addTo
import javax.swing.JLabel
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
                        column.addComponent(JXLabel(text = randomText()))
                    }
                    button(text = "Add 2 lines Label") {
                        column.addComponent(JLabel(randomText() + "!\n!" + randomText()))
                    }
                }
            )
        }.addTo(this)
//                (JTextArea(QpayStruct.toString())).addTo(this)
    }

}