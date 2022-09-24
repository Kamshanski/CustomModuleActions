package dev.itssho.module.uikit2.component.text

import com.intellij.ui.components.JBTextField
import java.awt.event.KeyEvent
import java.util.*

/** Проблема оригинально addDocChangeListener в том, что он разбивает вставку в выделение на 2 действия: удаление и вставку.
 * Класс нужен для того, чтобы устанавливать свой листенер, который обрабатывает новое значение только 1 раз, получая самое последнее значение8 */
class XTextField(text: String? = null, columns: Int = 0) : JBTextField(text, columns.coerceAtLeast(0)) {

    /** В конструкторе [JTextField] вызывается [setText], который тригерит лисенеры, которые ещё не инициализированы.
     * Поэтому сделал нулабельным, чтобы можно было проверить [changeListeners] на null перед вызовом */
    private val changeListeners: MutableList<(XTextField, String) -> Unit>? = mutableListOf()

    inline fun addTextChangeListener(crossinline listener: (String) -> Unit) {
        addTextChangeListener { _, str -> listener(str) }
    }

    fun addTextChangeListener(listener: (XTextField, String) -> Unit) {
        changeListeners?.add(listener)
    }

    fun removeTextChangeListener(listener: (XTextField, String) -> Unit) {
        changeListeners?.remove(listener)
    }

    private fun notifyListeners(textBefore: String, textAfter: String) {
        changeListeners?.forEach { it.invoke(this, textAfter) }
    }

    /** Здесь после каждого события, которое вызывает изменение состояние виждета, отслеживается изменение текста.
     * Если текст изменился, вызываются [changeListeners]. Для обработки событий с мышки и др. источников, нужно переопределить дргуие методы */
    override fun processKeyEvent(e: KeyEvent?) {
        executeWithChangeListeners {
            super.processKeyEvent(e)
        }
    }

    override fun setText(newText: String?) {
        executeWithChangeListeners {
            super.setText(newText)
        }
    }

    private inline fun executeWithChangeListeners(action: () -> Unit) {
        val textBefore = text
        action()
        val textAfter = text
        if (textAfter != textBefore) {
            notifyListeners(textBefore = textBefore, textAfter = textAfter)
        }
    }
}