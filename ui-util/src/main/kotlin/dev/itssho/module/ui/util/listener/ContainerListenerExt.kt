package dev.itssho.module.ui.util.listener

import java.awt.event.ContainerEvent
import java.awt.event.ContainerListener
import javax.swing.JComponent

fun <T : JComponent> T.addOnContainerChangedListener(listener: (ContainerEvent?) -> Unit): T =
    addContainerListener(listenerRemoved = listener, listenerAdded = listener)

fun <T : JComponent> T.addContainerListener(
    listenerAdded: (ContainerEvent?) -> Unit = {},
    listenerRemoved: (ContainerEvent?) -> Unit = {},
): T = apply {
    putContainerListener(listenerAdded, listenerRemoved)
}

fun <T : JComponent> T.putOnContainerChangedListener(listener: (ContainerEvent?) -> Unit): ContainerListener =
    putContainerListener(listenerRemoved = listener, listenerAdded = listener)

fun <T : JComponent> T.putContainerListener(
    listenerAdded: (ContainerEvent?) -> Unit = {},
    listenerRemoved: (ContainerEvent?) -> Unit = {},
): ContainerListener = object : ContainerListener {
    override fun componentAdded(e: ContainerEvent?) {
        listenerAdded(e)
    }

    override fun componentRemoved(e: ContainerEvent?) {
        listenerRemoved(e)
    }
}.also { addContainerListener(it) }

//fun <T : JComponent> T.addOnComponentAddedListener(listener: (ContainerEvent?) -> Unit = {}): T = addContainerListener(listenerAdded = listener)
//fun <T : JComponent> T.addOnComponentRemovedListener(listener: (ContainerEvent?) -> Unit = {}): T = addContainerListener(listenerRemoved = listener)