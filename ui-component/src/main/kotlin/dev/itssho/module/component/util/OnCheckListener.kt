package dev.itssho.module.component.util

import javax.swing.JCheckBox

inline fun JCheckBox.addOnCheckListener(crossinline listener: (Boolean) -> Unit) {
	addActionListener { listener.invoke(isSelected) }
}