package dev.itssho.module.component

import com.intellij.ui.components.JBScrollPane
import javax.swing.JComponent

fun Scroll(block: () -> JComponent): JBScrollPane = JBScrollPane().apply {
	setViewportView(block())
}

//fun <Здесть какой-то тип>.scrollable(block: () -> JComponent): JBScrollPane = JBScrollPane().apply {
//	setViewportView(block())
//}