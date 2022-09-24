package dev.itssho.module.uikit2.util.swing

import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
import javax.swing.JComponent

fun <T: JComponent> T.addOnResizedListener(listener: (ComponentEvent?) -> Unit): T =
    addComponentListener(listenerResized = listener)

fun <T: JComponent> T.addOnShownListener(listener: (ComponentEvent?) -> Unit): T =
    addComponentListener(listenerShown = listener)

fun <T: JComponent> T.addComponentListener(
    listenerResized: (ComponentEvent?) -> Unit = {},
    listenerMoved: (ComponentEvent?) -> Unit = {},
    listenerShown: (ComponentEvent?) -> Unit = {},
    listenerHidden: (ComponentEvent?) -> Unit = {},
): T = apply {
    addComponentListener(object : ComponentListener {
        override fun componentResized(e: ComponentEvent?) {
            listenerResized(e)
        }

        override fun componentMoved(e: ComponentEvent?) {
            listenerMoved(e)
        }

        override fun componentShown(e: ComponentEvent?) {
            listenerShown(e)
        }

        override fun componentHidden(e: ComponentEvent?) {
            listenerHidden(e)
        }
    })
}