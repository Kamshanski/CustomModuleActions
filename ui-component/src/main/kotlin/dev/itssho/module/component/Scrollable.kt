package dev.itssho.module.component

import com.intellij.ui.components.JBScrollPane
import javax.swing.JComponent
import javax.swing.ScrollPaneConstants

fun Scroll(vertical: Boolean = true, horizontal: Boolean = true, block: () -> JComponent): JBScrollPane = JBScrollPane().apply {
	verticalScrollBarPolicy = when (vertical) {
		true  -> ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
		false -> ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER
	}

	horizontalScrollBarPolicy = if (horizontal) {
		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
	} else {
		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
	}

	setViewportView(block())
}