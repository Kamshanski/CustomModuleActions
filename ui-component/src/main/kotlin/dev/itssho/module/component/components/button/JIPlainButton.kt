package dev.itssho.module.component.components.button

import com.intellij.ui.components.JBLabel
import javax.swing.Icon

class JIPlainButton(text: String? = null, icon: Icon? = null) : JBLabel(icon) {
	init {
		setText(text)
	}
}