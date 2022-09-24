package dev.itssho.module.ui.util.constructor

import dev.itssho.module.component.components.text.JITextField

inline fun jiTextField(text: String = "", editable: Boolean = true, crossinline onTextChanged: (String) -> Unit = {}) = JITextField(text).apply {
	isEditable = editable
	addTextChangeListener(onTextChanged)
}