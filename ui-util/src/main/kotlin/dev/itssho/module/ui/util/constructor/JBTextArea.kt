package dev.itssho.module.ui.util.constructor

import com.intellij.ui.components.JBTextArea

inline fun jbTextArea(text: String = "", editable: Boolean = false) = JBTextArea().apply {
	setText(text)
	isEditable = editable
}