package dev.itssho.module.ui.util.constructor

import com.esotericsoftware.tablelayout.swing.Table
import dev.itssho.module.ui.util.container.addAllToCells
import javax.swing.JComponent

inline fun table(block: Table.() -> Unit) = Table().apply(block)

inline fun tableLine(vertical: Boolean, components: List<JComponent>) = Table().apply {
	addAllToCells(components, vertical = vertical) { compoent, cell -> }
}


inline fun tableRow(components: List<JComponent>) = tableLine(components = components, vertical = false)
inline fun tableColumn(components: List<JComponent>) = tableLine(components = components, vertical = true)

inline fun tableRow(vararg components: JComponent) = tableRow(components = components.toList())
inline fun tableColumn(vararg components: JComponent) = tableColumn(components = components.toList())