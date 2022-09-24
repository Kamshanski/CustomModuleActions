package dev.itssho.module.component.components.simple

import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JComponent

class JISpace : JComponent() {

    companion object {
        private val ZERO_DIMEN = Dimension(0,0)
    }

    override fun paint(g: Graphics?) { /* Do nothing */ }

    override fun getWidth(): Int = getSpaceSize().width
    override fun getHeight(): Int = getSpaceSize().height

    override fun getPreferredSize(): Dimension = getSpaceSize()
    override fun getMinimumSize(): Dimension = getSpaceSize()
    override fun getMaximumSize(): Dimension = getSpaceSize()

    private inline fun getSpaceSize(): Dimension = ZERO_DIMEN
}