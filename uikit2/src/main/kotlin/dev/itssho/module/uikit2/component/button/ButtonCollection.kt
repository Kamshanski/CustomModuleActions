package dev.itssho.module.uikit2.component.button

import javax.swing.JToggleButton

private val BUTTON_COLLECTION_KEY = "JToggleButton_ButtonCollection"
var JToggleButton.collection: ButtonCollection?
    get() = getClientProperty(BUTTON_COLLECTION_KEY) as? ButtonCollection
    set(value) = putClientProperty(BUTTON_COLLECTION_KEY, value)

class ButtonCollection(private val buttons: List<JToggleButton>) {

    private val listeners: MutableList<(JToggleButton?, Int) -> Unit> = mutableListOf()

    private var _selected = buttons.indexOfFirst { it.isSelected }
    var selected: Int
        get() = _selected
        set(value) = selectAt(value)

    init {
        buttons.forEachIndexed { i, btn ->
            btn.collection = this
            btn.model = XToggleButtonModel { newState -> (selected != i || (selected == i && newState == true)) }
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