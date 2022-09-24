package dev.itssho.module.ui.util.constructor

import com.intellij.ui.JBColor
import dev.itssho.module.component.components.simple.JILabel
import dev.itssho.module.component.value.Gravity
import java.awt.Color
import java.awt.Font
import javax.swing.Icon

inline fun jiLabel(
	text: String = "",
	textColor: Color? = JBColor.LIGHT_GRAY,
	tooltip: String = "",
	icon: Icon? = null,
	textAlignment: Gravity = Gravity.START,
	font: Font? = null,
) = JILabel(
	text = text,
	textColor = textColor,
	tooltip = tooltip,
	icon = icon,
	textAlignment = textAlignment,
	font = font
)