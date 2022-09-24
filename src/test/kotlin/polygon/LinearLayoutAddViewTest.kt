package polygon

import common.runTestFrame
import dev.itssho.module.feature.qpay.module.common.ui.QpayStruct
import dev.itssho.module.uikit.component.text.JXLabel
import dev.itssho.module.uikit.dsl.button.button
import dev.itssho.module.uikit.dsl.label.label
import dev.itssho.module.uikit.dsl.panel.column
import dev.itssho.module.uikit.dsl.panel.panel
import dev.itssho.module.uikit.dsl.panel.row
import dev.itssho.module.uikit.extensions.addTo
import javax.swing.JLabel
import javax.swing.JTextArea
import kotlin.random.Random

/** Вывод:
 * LinearLayoutPanel надо updateUI, чтобы отобразить добавленные компоненты
 * JLabel не работает с несколькими строками
 */
val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun randomText(len: Int? = null, maxLen: Int = 50) = (1 .. (len ?: Random.nextInt(from = 1, until = maxLen)))
    .map { i -> Random.nextInt(0, charPool.size) }
    .map(charPool::get)
    .joinToString("")

fun main() = runTestFrame {
    panel {
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
        (JTextArea(QpayStruct.toString())).addTo(this)
    }
}