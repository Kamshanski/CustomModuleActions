package dev.itssho.module.qpay.module.structure.ui.delegate

import dev.itssho.module.component.components.button.JIPlainButton
import javax.swing.JComponent
import javax.swing.JLabel

class TreeItem(
	val front: JLabel,
	val main: JComponent,
	val back: JLabel,
	val actions: List<JIPlainButton>,
) {

	private val allComponents by lazy { listOfNotNull(front, main, back) + actions }

	fun asList(): List<JComponent> =
		allComponents
}