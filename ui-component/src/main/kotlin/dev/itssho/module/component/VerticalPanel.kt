package dev.itssho.module.component

import org.jdesktop.swingx.VerticalLayout
import javax.swing.JPanel

fun VerticalPanel(gap: Int = 0, block: (JPanel.() -> Unit)? = null): JPanel =
	JPanel().apply {
		layout = VerticalLayout(gap)
		block?.invoke(this)
	}