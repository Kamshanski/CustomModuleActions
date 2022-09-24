package dev.itssho.module.ui.util.constructor

import dev.itssho.module.component.components.button.JIPlainButton
import dev.itssho.module.component.util.addOnClickListener
import javax.swing.Icon
import javax.swing.JButton

fun jButton(text: String = "", icon: Icon? = null, action: JButton.() -> Unit): JButton = JButton(text, icon).apply {
	addActionListener { action() }
}

fun jiPlainButton(icon: Icon? = null, text: String? = "", actionBlock: (() -> Unit)? = null): JIPlainButton = JIPlainButton(text, icon).apply {
	actionBlock?.let { addOnClickListener(it) }
}