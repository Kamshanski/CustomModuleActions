package swing

import javax.swing.DefaultComboBoxModel
import javax.swing.JComboBox

/** @param selectIndex null means try to save previous. If inex is out of items bounds then set 0 */
fun <T> JComboBox<T>.setItems(items: Array<T>, selectIndex: Int? = null) {
	val newModel = DefaultComboBoxModel(items)
	val selectIndex = (selectIndex ?: this.selectedIndex).coerceIn(minimumValue = 0, maximumValue = items.size)

	model = newModel
	selectedIndex = selectIndex
}

fun JComboBox<*>.setSelectedIndexIfDiffer(index: Int) {
	if (selectedIndex != index) {
		selectedIndex = index
	}
}