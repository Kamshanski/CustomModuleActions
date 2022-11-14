package common

import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.extensions.addTo
import dev.itssho.module.uikit.modification.Modifier
import javax.swing.JFrame
import javax.swing.WindowConstants

fun JFrame.framePanel(modifier: Modifier = Modifier(), panelBuilder: JXLinearPanel.() -> Unit) =
    dev.itssho.module.uikit.dsl.panel.panel(modifier, panelBuilder)
        .addTo(this)

fun runTestFrame(action: JFrame.() -> Unit) {
    val frame = JFrame("Title")

    frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

    frame.setSize(700, 700)

    frame.action()

    frame.isVisible = true
}