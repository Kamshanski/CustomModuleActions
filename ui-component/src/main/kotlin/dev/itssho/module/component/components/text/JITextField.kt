package dev.itssho.module.component.components.text

import com.intellij.ui.components.JBTextField
import java.awt.event.InputMethodEvent
import java.awt.event.KeyEvent

/** Проблема оригинального addDocChangeListener в том, что он разбивает вставку в выделение на 2 действия: удаление и вставку.
 * Класс нужен для того, чтобы устанавливать свой листенер, который обрабатывает новое значение только 1 раз, получая самое последнее значение8 */
class JITextField(text: String = "", columns: Int = 0) : JBTextField(text, columns.coerceAtLeast(0)) {

	private var previousText: String = text

	/** В конструкторе [JTextField] вызывается [setText], который тригерит лисенеры, которые ещё не инициализированы.
	 * Поэтому сделал нулабельным, чтобы можно было проверить [changeListeners] на null перед вызовом */
	@Suppress("RedundantNullableReturnType")
	private val changeListeners: MutableList<(JITextField, String) -> Unit>? = mutableListOf()

	inline fun addTextChangeListener(crossinline listener: (String) -> Unit) {
		addTextChangeListener { _, str -> listener(str) }
	}

	fun addTextChangeListener(listener: (JITextField, String) -> Unit) {
		changeListeners?.add(listener)
	}

	fun removeTextChangeListener(listener: (JITextField, String) -> Unit) {
		changeListeners?.remove(listener)
	}

	override fun processInputMethodEvent(e: InputMethodEvent?) {
		super.processInputMethodEvent(e)
		notifyListenersOnChange()
	}

	/** Здесь после каждого события, которое вызывает изменение состояние виждета, отслеживается изменение текста.
	 * Если текст изменился, вызываются [changeListeners]. Для обработки событий с мышки и др. источников, нужно переопределить дргуие методы */
	override fun processKeyEvent(e: KeyEvent?) {
		super.processKeyEvent(e)
		notifyListenersOnChange()
	}

	override fun setText(newText: String?) {
		super.setText(newText)
		notifyListenersOnChange()
	}

	private inline fun notifyListenersOnChange() {
		if (previousText != text) {
			val textBefore = previousText
			previousText = text

			notifyListeners(textBefore = textBefore, textAfter = text)
		}
	}

	private fun notifyListeners(textBefore: String, textAfter: String) {
		changeListeners?.forEach { it.invoke(this, textAfter) }
	}
}