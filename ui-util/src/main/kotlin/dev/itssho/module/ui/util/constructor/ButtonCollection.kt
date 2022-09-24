package dev.itssho.module.ui.util.constructor

import dev.itssho.module.component.components.select.ButtonCollection
import dev.itssho.module.ui.util.listener.putOnContainerChangedListener
import java.awt.event.ContainerEvent
import javax.swing.JComponent
import javax.swing.JToggleButton

fun <C : JComponent> C.collectButtons(buttonGroupComposer: C.() -> Unit): ButtonCollection {

	val buttons: MutableList<JToggleButton> = mutableListOf()

	val listener = putOnContainerChangedListener { event ->
		val button = event?.child as? JToggleButton ?: return@putOnContainerChangedListener
		when (event.id) {
			ContainerEvent.COMPONENT_ADDED   -> buttons.add(button)
			ContainerEvent.COMPONENT_REMOVED -> buttons.remove(button)
		}
	}
	buttonGroupComposer()
	removeContainerListener(listener)

	return buttonsCollection(buttons)
}

fun buttonsCollection(buttons: List<JToggleButton>): ButtonCollection {
	return ButtonCollection(buttons)
}