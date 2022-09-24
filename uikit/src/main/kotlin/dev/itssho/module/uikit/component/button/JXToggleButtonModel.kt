package dev.itssho.module.uikit.component.button

import java.awt.event.ItemEvent
import javax.swing.JToggleButton

class JXToggleButtonModel(val selectionReviewer: (newState: Boolean) -> Boolean) : JToggleButton.ToggleButtonModel() {

    override fun setSelected(newState: Boolean) {
        if (newState == isSelected) {
            return
        }

        val newStateAllowed = selectionReviewer(newState)

        if (newStateAllowed) {
            // Отсюда и до конца копипата из JToggleButton.ToggleButtonModel
            changeStateMask(SELECTED, newState)

            // Send ChangeEvent
            fireStateChanged()

            // Send ItemEvent
            fireItemStateChanged(
                ItemEvent(this,
                          ItemEvent.ITEM_STATE_CHANGED,
                          this,
                          if (this.isSelected) ItemEvent.SELECTED else ItemEvent.DESELECTED))
        }
    }

    private fun changeStateMask(stateId: Int, newState: Boolean) {
        stateMask = if (newState) {
            stateMask or stateId
        } else {
            stateMask and stateId.inv()
        }
    }
}