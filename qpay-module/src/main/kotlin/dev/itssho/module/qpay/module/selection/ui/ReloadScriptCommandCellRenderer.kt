package dev.itssho.module.qpay.module.selection.ui

import dev.itssho.module.component.resources.Icons
import dev.itssho.module.qpay.module.selection.presentation.ReloadScriptCommand
import java.awt.Component
import java.util.EventObject
import javax.swing.JComponent
import javax.swing.JTable
import javax.swing.event.CellEditorListener
import javax.swing.event.ChangeEvent
import javax.swing.event.EventListenerList
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.table.TableCellEditor

/** Часть кода скопипизжена из [javax.swing.AbstractCellEditor] */
internal class ReloadScriptCommandCellRenderer(
	private val hintText: String,
	private val onClickListener: (ReloadScriptCommand) -> Unit,
) : DefaultTableCellRenderer(), TableCellEditor {

	override fun getTableCellRendererComponent(table: JTable, value: Any, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int): Component {
//		val command = value as ReloadScriptCommand
		val component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column) as JComponent
//		component.addOnClickListener { onClickListener(command) }
		this.icon = Icons.UPDATE.ic16px
		component.toolTipText = hintText
		return component
	}

	private var listenerList = EventListenerList()

	@Transient
	private var changeEvent: ChangeEvent? = null

	override fun getTableCellEditorComponent(table: JTable?, value: Any?, isSelected: Boolean, row: Int, column: Int): Component {
		this.icon = Icons.UPDATE.ic16px
		return this
	}

	override fun getCellEditorValue(): Any =
		Unit

	override fun isCellEditable(e: EventObject?): Boolean = true

	override fun shouldSelectCell(anEvent: EventObject?): Boolean = true

	override fun stopCellEditing(): Boolean {
		fireEditingStopped()
		return true
	}

	override fun cancelCellEditing() { fireEditingCanceled() }

	override fun addCellEditorListener(l: CellEditorListener) { listenerList.add(CellEditorListener::class.java, l) }

	override fun removeCellEditorListener(l: CellEditorListener) { listenerList.remove(CellEditorListener::class.java, l) }

	private fun fireEditingStopped() {
		val listeners = listenerList.listenerList

		if (listeners.size == 0) return

		var i = listeners.size - 2
		while (i >= 0) {
			if (listeners[i] === CellEditorListener::class.java) {
				if (changeEvent == null) changeEvent = ChangeEvent(this)
				(listeners[i + 1] as CellEditorListener).editingStopped(changeEvent)
			}
			i -= 2
		}
	}

	private fun fireEditingCanceled() {
		val listeners = listenerList.listenerList

		if (listeners.size == 0) return

		var i = listeners.size - 2
		while (i >= 0) {
			if (listeners[i] === CellEditorListener::class.java) {
				if (changeEvent == null) changeEvent = ChangeEvent(this)
				(listeners[i + 1] as CellEditorListener).editingCanceled(changeEvent)
			}
			i -= 2
		}
	}
}