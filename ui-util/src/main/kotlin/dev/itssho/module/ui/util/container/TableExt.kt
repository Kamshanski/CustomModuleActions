package dev.itssho.module.ui.util.container

import com.esotericsoftware.tablelayout.Cell
import com.esotericsoftware.tablelayout.swing.Table
import javax.swing.JComponent

fun <T: JComponent> T.insertToCell(table: Table): T = also { table.addCell(it) }

inline fun <T: JComponent> Table.addAllToCells(views: List<T>, vertical: Boolean = false, crossinline block: (T, Cell<*>) -> Unit = {_, _ -> }) {
	val last = views.lastIndex

	for (i in views.indices) {
		val view = views[i]

		block(view, addCell(view))

		if (vertical && i < last) {
			row()
		}
	}
}

inline fun Cell<*>.padHorizontal(pad: Float) = padLeft(pad).padRight(pad)
inline fun Cell<*>.padVertical(pad: Float) = padTop(pad).padBottom(pad)


inline fun Table.cellsLeft(): Table = apply { cells.forEach { it.left() } }
inline fun Table.cellsRight(): Table = apply { cells.forEach { it.right() } }
inline fun Table.cellsTop(): Table = apply { cells.forEach { it.top() } }
inline fun Table.cellsBottom(): Table = apply { cells.forEach { it.bottom() } }