import com.intellij.ui.components.JBLayeredPane
import com.intellij.ui.components.JBScrollPane
import dev.itssho.module.uikit2.component.XLinearPanel
import dev.itssho.module.uikit2.dsl.button
import dev.itssho.module.uikit2.dsl.column
import dev.itssho.module.uikit2.dsl.label
import dev.itssho.module.uikit2.dsl.panel
import dev.itssho.module.uikit2.dsl.select.checkBox
import dev.itssho.module.uikit2.dsl.select.groupSingleSelectionButtons
import dev.itssho.module.uikit2.dsl.select.radioButton
import dev.itssho.module.uikit2.modify.Modifier
import dev.itssho.module.uikit2.modify.modification.*
import dev.itssho.module.uikit2.util.swing.addTo
import dev.itssho.module.component.value.Gravity
import dev.itssho.module.component.value.Orientation
import java.awt.Container
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants

fun JFrame.framePanel(modifier: Modifier = Modifier, panelBuilder: XLinearPanel.() -> Unit) =
    XLinearPanel(Orientation.VERTICAL).also {
        it.panelBuilder()
        add(it)
    }

inline fun runTestFrame(action: () -> Container) {
    val frame = JFrame("Title")

    frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

    frame.setSize(700, 700)
//    val scrollPane = JBScrollPane()
//    frame.contentPane = scrollPane
//
//    val contentPanel = action()
//    scrollPane.setViewportView(contentPanel)

    frame.add(action())

    frame.isVisible = true
}

class Counter(startValue: Int = 0) {

    private var value: Int = startValue

    fun next(): Int = value++

    fun nextStr(): String = next().toString()
}

val count = Counter(0)

fun main() = runTestFrame {
    panel {
        val col = column(Modifier.fillWidth(1).fillHeight(1).gravity(Gravity.TOP_START), isScrollable = true) {
            label(text = "fdgdfgdgfffffffffffff")
        }
        button(Modifier.gravity(Gravity.START), text = "ADD") { col.label(text = count.nextStr()) }
        button(Modifier.gravity(Gravity.END), text = "REMOVE") {
            val lastComponentIndex = col.componentCount - 1
            if (col.getComponent(lastComponentIndex) is JButton) {
                label(text = "NO LABELS IN FRAME")
            } else {
                col.remove(lastComponentIndex)
            }
        }
        groupSingleSelectionButtons {
            label(text = "Radio")
            radioButton(text = "One", isSelected = false)
            radioButton(text = "Two", isSelected = false)
            label(text = "Check")
            checkBox(text = "Three", isSelected = false)
        }
    }
}