package swing

import javax.swing.AbstractButton

fun AbstractButton.removeAllActionListeners() {
	val listeners = actionListeners.toList()
	listeners.forEach {
		removeActionListener(it)
	}
}