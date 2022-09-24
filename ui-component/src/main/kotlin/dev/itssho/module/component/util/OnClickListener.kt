package dev.itssho.module.component.util

import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JComponent

fun JComponent.addOnClickListener(listener: () -> Unit) {
	addMouseListener(object : MouseListener {
		override fun mouseClicked(e: MouseEvent?) {
			listener()
		}

		override fun mousePressed(e: MouseEvent?) {}
		override fun mouseReleased(e: MouseEvent?) {}
		override fun mouseEntered(e: MouseEvent?) {}
		override fun mouseExited(e: MouseEvent?) {}
	})
}