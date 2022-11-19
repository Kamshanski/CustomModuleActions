package dev.itssho.module.qpay.module.selection.ui

import dev.itssho.module.qpay.module.selection.presentation.ReloadScriptCommand
import javax.swing.table.DefaultTableModel

class ScriptsTableModel(
	private val columnNamesAndTypes: List<ColumModel>,
	private val isEditable: ((row: Int, column: Int) -> Boolean)? = null,
) : DefaultTableModel(columnNamesAndTypes.map { it.name }.toTypedArray(), 0) {

	override fun getColumnCount(): Int {
		return columnNamesAndTypes.size
	}

	override fun getColumnName(column: Int): String {
		return columnNamesAndTypes[column].name
	}

	override fun getColumnClass(columnIndex: Int): Class<*> {
		return columnNamesAndTypes[columnIndex].type
	}

	override fun isCellEditable(row: Int, column: Int): Boolean {
		return isEditable?.invoke(row, column) ?: false
	}

	override fun setValueAt(aValue: Any?, row: Int, column: Int) {
		if (columnNamesAndTypes[column].type == ReloadScriptCommand::class.java) {
			return
		}
		super.setValueAt(aValue, row, column)
	}

	class ColumModel(val name: String, val type: Class<*>)
}