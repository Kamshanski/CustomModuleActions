package dev.itssho.module.uikit.extensions

import dev.itssho.module.uikit.component.panel.JXLinearPanel
import java.awt.Container
import javax.swing.JComponent

fun <T : JComponent> T.addTo(container: Container): T = apply {
    when (container) {
        is JXLinearPanel -> container.addComponent(this)
        else             -> container.add(this)
    }
}