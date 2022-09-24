package dev.itssho.module.component.util

import com.intellij.openapi.ui.ComboBox
import java.awt.event.ItemEvent

inline fun <reified T> ComboBox<T>.addOnSelectListener(crossinline listener: (T) -> Unit) {
	addItemListener { event ->
		if (event.stateChange == ItemEvent.SELECTED) {
			listener(event.item as T)
		}
	}
}