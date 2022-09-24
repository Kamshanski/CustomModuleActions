package dev.itssho.module.uikit2.dsl.select

import dev.itssho.module.uikit2.component.XLinearPanel
import dev.itssho.module.uikit2.component.button.ButtonCollection
import dev.itssho.module.uikit2.modify.Modifier
import dev.itssho.module.uikit2.util.swing.*
import java.awt.event.ContainerEvent
import javax.swing.JRadioButton
import javax.swing.JToggleButton

fun XLinearPanel.radioButton(modifier: Modifier = Modifier, text: String, isSelected: Boolean, listener: ((Boolean) -> Unit)? = null): JRadioButton =
    JRadioButton(text)
        .also { btn ->
            if (listener == null) return@also
            btn.addActionListener { listener.invoke(btn.isSelected) }
        }
        .addTo(this, modifier)

fun XLinearPanel.groupSingleSelectionButtons(buttonGroupComposer: XLinearPanel.() -> Unit): ButtonCollection {
    val buttons: MutableList<JToggleButton> = mutableListOf()

    val listener = putOnContainerChangedListener { event ->
        val button = event?.child as? JToggleButton ?: return@putOnContainerChangedListener
        when (event.id) {
            ContainerEvent.COMPONENT_ADDED -> buttons.add(button)
            ContainerEvent.COMPONENT_REMOVED -> buttons.remove(button)
        }
    }
    buttonGroupComposer()
    removeContainerListener(listener)

    return ButtonCollection(buttons)
}
