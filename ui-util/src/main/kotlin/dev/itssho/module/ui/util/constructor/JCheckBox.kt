package dev.itssho.module.ui.util.constructor

import javax.swing.JCheckBox

fun jCheckBox(
	text: String,
	isSelected: Boolean = false,
	listener: ((Boolean) -> Unit)? = null,
): JCheckBox =
	JCheckBox(text, isSelected).also { btn ->
		if (listener == null) return@also
		btn.addActionListener { listener.invoke(btn.isSelected) }
	}