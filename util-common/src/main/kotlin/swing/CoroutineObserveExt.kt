package swing

import coroutine.observe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import javax.swing.JLabel
import javax.swing.text.JTextComponent

fun StateFlow<String>.observeText(scope: CoroutineScope, label: JLabel) {
	this.observe(scope) { label.text = this.value }
}

fun StateFlow<String>.observeText(scope: CoroutineScope, label: JTextComponent) {
	this.observe(scope) { label.text = this.value }
}