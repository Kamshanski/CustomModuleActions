package dev.itssho.module.uikit.extensions

import java.awt.event.ComponentEvent
import java.awt.event.ContainerEvent
import java.awt.event.ContainerListener
import javax.swing.JComponent

fun <T : JComponent> T.addOnContainerChangedListener(listener: (ComponentEvent?) -> Unit): T =
    addContainerListener(listenerRemoved = listener, listenerAdded = listener)

fun <T : JComponent> T.addContainerListener(
    listenerAdded: (ComponentEvent?) -> Unit = {},
    listenerRemoved: (ComponentEvent?) -> Unit = {},
): T = apply {
    addContainerListener(object : ContainerListener {
        override fun componentAdded(e: ContainerEvent?) {
            listenerAdded(e)
        }

        override fun componentRemoved(e: ContainerEvent?) {
            listenerRemoved(e)
        }
    })
}