package dev.itssho.module.ui.util.constructor

import dev.itssho.module.component.value.Orientation
import javax.swing.JProgressBar
import javax.swing.SwingConstants

inline fun jProgressBar(orientation: Orientation = Orientation.HORIZONTAL, min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE) = JProgressBar(
	when (orientation) {
		Orientation.HORIZONTAL -> SwingConstants.HORIZONTAL
		Orientation.VERTICAL   -> SwingConstants.VERTICAL
	},
	min,
	max,
).apply {}