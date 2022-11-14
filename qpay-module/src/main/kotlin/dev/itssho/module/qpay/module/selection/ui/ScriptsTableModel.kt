package dev.itssho.module.qpay.module.selection.ui

import javax.swing.table.DefaultTableModel

class ScriptsTableModel(
	private val columnNamesAndTypes: List<ColumModel>,
	private val isEditable: ((Int, Int) -> Boolean)? = null,
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

	class ColumModel(val name: String, val type: Class<*>)
}