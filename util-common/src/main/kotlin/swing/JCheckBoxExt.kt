package swing

import javax.swing.JCheckBox

fun JCheckBox.setSelectedIfDiffer(check: Boolean) {
	if (isSelected != check) {
		isSelected = check
	}
}