package dev.itssho.module.uikit2.component.text

import com.intellij.ui.JBColor
import dev.itssho.module.component.value.Gravity
import java.awt.Color
import java.awt.Font
import javax.swing.Icon
import javax.swing.JLabel

// TODO сделать обчыный label
/** Скопитырино из com.intellij.ui.ErrorLabel
 * - не правда( Там оказалось не то, что нужно, поэтому ничего не скопитырено */
class XLabel(
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
        font?.let { this.font = it }
        isOpaque = false
        horizontalAlignment = when (textAlignment) {
            Gravity.END   -> RIGHT
            Gravity.START -> LEFT
            else          -> CENTER
        }
        verticalAlignment = when (textAlignment) {
            Gravity.BOTTOM -> BOTTOM
            Gravity.TOP    -> TOP
            else           -> CENTER
        }
    }

//    override fun getMinimumSize(): Dimension {
//        if (isMinimumSizeSet) {
//            return super.getMinimumSize()
//        }
//
//        val preferred by lazy { preferredSize }
//        val fontMetrics by lazy { getFontMetrics(font) }
//
//        val minHeight = if (linearConstraints.height <= 0)
//            fontMetrics.height
//        else
//            preferred.height
//
//        val minWidth = if (linearConstraints.width <= 0) {
//            fontMetrics.stringWidth(MIN_TEXT)
//        } else {
//            preferred.width
//        }
//
//        if (minWidth > 0 && minHeight > 0) {
//            return Dimension(minWidth, minHeight)
//        }
//
//        return super.getMinimumSize()
//    }

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