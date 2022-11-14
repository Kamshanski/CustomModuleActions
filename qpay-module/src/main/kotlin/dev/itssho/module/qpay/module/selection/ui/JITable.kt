package dev.itssho.module.qpay.module.selection.ui

import com.intellij.ui.table.JBTable
import java.awt.Component
import java.util.EventObject
import javax.swing.JTable
import javax.swing.event.CellEditorListener
import javax.swing.table.TableCellEditor
import javax.swing.table.TableCellRenderer

class JITable : JBTable() {

	private var rendererListener: ((Component, Int, Int) -> Unit)? = null
	private var editorListener: ((Component, Int, Int) -> Unit)? = null

	fun setRendererListener(listener: ((Component, Int, Int) -> Unit)?) {
		rendererListener = listener
	}

	fun setEditorListener(listener: ((Component, Int, Int) -> Unit)?) {
		editorListener = listener
	}

	override fun getCellRenderer(row: Int, column: Int): TableCellRenderer? {
		val renderer = super.getCellRenderer(row, column)

		return when (renderer) {
			null                  -> null
			is ListenableRenderer -> renderer
			else                  -> ListenableRenderer(renderer)
		}
	}

	override fun getCellEditor(): TableCellEditor? {
		val editor = super.getCellEditor()
		return when (editor) {
			null                -> null
			is ListenableEditor -> editor
			else                -> ListenableEditor(editor)
		}
	}

	override fun getCellEditor(row: Int, column: Int): TableCellEditor? {
		val editor = super.getCellEditor(row, column)
		return when (editor) {
			null                -> null
			is ListenableEditor -> editor
			else                -> ListenableEditor(editor)
		}
	}

	private inner class ListenableRenderer(private val renderer: TableCellRenderer) : TableCellRenderer {

		override fun getTableCellRendererComponent(table: JTable?, value: Any?, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int): Component {
			val component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
			rendererListener?.invoke(component, row, column)
			return component
		}
	}

	private inner class ListenableEditor(private val editor: TableCellEditor) : TableCellEditor {

		override fun getCellEditorValue(): Any =
			editor.cellEditorValue

		override fun isCellEditable(anEvent: EventObject?): Boolean =
			editor.isCellEditable(anEvent)

		override fun shouldSelectCell(anEvent: EventObject?): Boolean =
			editor.shouldSelectCell(anEvent)

		override fun stopCellEditing(): Boolean =
			editor.stopCellEditing()

		override fun cancelCellEditing() {
			editor.cancelCellEditing()
		}

		override fun addCellEditorListener(l: CellEditorListener?) {
			editor.addCellEditorListener(l)
		}

		override fun removeCellEditorListener(l: CellEditorListener?) {
			editor.removeCellEditorListener(l)
		}

		override fun getTableCellEditorComponent(table: JTable?, value: Any?, isSelected: Boolean, row: Int, column: Int): Component {
			val component = editor.getTableCellEditorComponent(table, value, isSelected, row, column)
			editorListener?.invoke(component, row, column)
			return component
		}
	}
}