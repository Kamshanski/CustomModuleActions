package dev.itssho.module.ui.util.constructor

import com.intellij.openapi.ui.ComboBox
import java.awt.event.ItemEvent

inline fun <reified T : Any> jSelector(
	items: List<T>,
	initialSelectedItem: Int = 0,
	noinline listener: ((T) -> Unit)? = null,
): ComboBox<T> = ComboBox(items.toTypedArray()).also { btn ->
	btn.selectedIndex = initialSelectedItem

	if (listener != null) {
		btn.addItemListener { event ->
			if (event.stateChange == ItemEvent.SELECTED) {
				listener(event.item as T)
			}
		}
	}
}