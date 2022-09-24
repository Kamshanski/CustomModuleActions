package dev.itssho.module.qpay.module.structure.ui.delegate

import javax.swing.ComboBoxModel

fun <T> compareItems(list: List<T>, model: ComboBoxModel<T>): Boolean {
	if (list.size != model.size) {
		return false
	}

	for (i in list.indices) {
		val item = list[i]
		val comboBoxItem = model.getElementAt(i)
		if (item != comboBoxItem) {
			return false
		}
	}

	return true
}