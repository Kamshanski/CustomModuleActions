package dev.itssho.module.ui.util.constructor

import dev.itssho.module.component.value.Orientation
import javax.swing.JProgressBar
import javax.swing.SwingConstants

inline fun jProgressBar(orientation: Orientation = Orientation.HORIZONTAL, min: Int = 0, max: Int = 100) = JProgressBar(
	when (orientation) {
		Orientation.HORIZONTAL -> SwingConstants.HORIZONTAL
		Orientation.VERTICAL   -> SwingConstants.VERTICAL
	},
	min,
	max,
).apply {}