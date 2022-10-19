package dev.itssho.module.component.components.simple

import com.intellij.ui.JBColor
import dev.itssho.module.component.value.Gravity
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import javax.swing.Icon
import javax.swing.JLabel
import javax.swing.SwingConstants

// TODO сделать обчыный label
/** Скопитырино из com.intellij.ui.ErrorLabel
 * - не правда( Там оказалось не то, что нужно, поэтому ничего не скопитырено */
class JILabel(
	text: String? = null,
	private var textColor: Color? = JBColor.LIGHT_GRAY,
	private var tooltip: String = "",
	icon: Icon? = null,
	private val textAlignment: Gravity = Gravity.START,
	font: Font? = null,
) : JLabel(text, icon, LEFT) {

	companion object {

		private const val MIN_TEXT = " "
	}

	init {
		font?.also { this.font = it }
		isOpaque = false
		horizontalAlignment = when (textAlignment) {
			Gravity.START,
			Gravity.TOP_START,
			Gravity.BOTTOM_START -> SwingConstants.LEFT
			Gravity.END,
			Gravity.TOP_END,
			Gravity.BOTTOM_END   -> SwingConstants.RIGHT
			else                 -> SwingConstants.CENTER
		}
		verticalAlignment = when (textAlignment) {
			Gravity.BOTTOM,
			Gravity.BOTTOM_END,
			Gravity.BOTTOM_START -> SwingConstants.BOTTOM
			Gravity.TOP,
			Gravity.TOP_END,
			Gravity.TOP_START    -> SwingConstants.TOP
			else                 -> SwingConstants.CENTER
		}
	}

	override fun getMinimumSize(): Dimension {
		if (isMinimumSizeSet) {
			return super.getMinimumSize()
		}

		val fontMetrics = getFontMetrics(font)

		val minHeight = fontMetrics.height

		val minWidth = fontMetrics.stringWidth(MIN_TEXT)

		if (minWidth > 0 && minHeight > 0) {
			return Dimension(minWidth, minHeight)
		}

		return super.getMinimumSize()
	}

	// TODO вынести в ext
	fun setErrorText(text: String?) {
		setColoredText(text, JBColor.ORANGE)
	}

	fun setWarningText(text: String?) {
		setColoredText(text, JBColor.YELLOW)
	}

	fun setNotificationText(text: String?) {
		setColoredText(text, JBColor.YELLOW)
	}

	fun clearText() {
		setColoredText("", textColor)
	}

	fun setColoredText(text: String?, color: Color?) {
		this.text = text
		foreground = color
	}
}