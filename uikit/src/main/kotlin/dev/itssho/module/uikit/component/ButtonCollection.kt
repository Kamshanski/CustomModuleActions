package dev.itssho.module.uikit.component

import dev.itssho.module.uikit.component.button.JXToggleButtonModel
import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.modification.Modifier
import dev.itssho.module.uikit.dsl.select.radioButton
import javax.swing.JRadioButton
import javax.swing.JToggleButton

class ButtonCollection(private val buttons: List<JToggleButton>) {

    private val listeners: MutableList<(JToggleButton?, Int) -> Unit> = mutableListOf()

    private var _selected = buttons.indexOfFirst { it.isSelected }
    var selected: Int
        get() = _selected
        set(value) = selectAt(value)

    init {
        buttons.forEachIndexed { i, btn ->
            btn.model = JXToggleButtonModel { newState -> (selected != i || (selected == i && newState == true)) }
            btn.addActionListener {
                if (btn.isSelected) {
                    updateSelection(i)
                }
            }
        }
    }

    fun addOnButtonSelectedListener(listener: (JToggleButton?, Int) -> Unit) = apply {
        listeners.add(listener)
    }

    fun removeOnButtonSelectedListener(listener: (JToggleButton?, Int) -> Unit) = apply {
        listeners.remove(listener)
    }

    private fun selectAt(index: Int) {
        if (selected == index) {
            return
        }

        if (index < 0) {
            updateSelection(index)
        } else {
            check(index in buttons.indices)
            updateSelection(index)
        }
    }

    private fun updateSelection(selectedIndex: Int) {
        _selected = selectedIndex

        buttons.forEachIndexed { index, btn ->
            btn.switchTo(index == selectedIndex)
        }
        listeners.forEach { listener -> listener(buttons.getOrNull(selectedIndex), selectedIndex) }
    }

    private fun JToggleButton.switchTo(selected: Boolean) {
        if (isSelected != selected) {
            isSelected = selected
        }
    }
}

interface ButtonCollectionBuilder {

    fun JXLinearPanel.addRadioButton(
        modifier: Modifier = Modifier(),
        text: String,
        selected: Boolean = false,
        listener: (Boolean) -> Unit = {},
    ): JRadioButton
}

class ButtonCollectionBuilderImpl : ButtonCollectionBuilder {

    private val buttons: MutableList<JToggleButton> = mutableListOf()

    override fun JXLinearPanel.addRadioButton(modifier: Modifier, text: String, selected: Boolean, listener: (Boolean) -> Unit): JRadioButton {
        val btn = radioButton(modifier, text, selected, listener)
        buttons.add(btn)
        return btn
    }

    fun build(): ButtonCollection = ButtonCollection(buttons)
}