package dev.itssho.module.uikit.component

import dev.itssho.module.uikit.layout.swan.linear.linearConstraints
import dev.itssho.module.uikit.util.X
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JComponent

class JXSpace : JComponent() {

    override fun paint(g: Graphics?) { /* Do nothing */
    }

    override fun getWidth(): Int = linearConstraints.calculatedWidth.coerceAtLeast(0)
    override fun getHeight(): Int = linearConstraints.calculatedWidth.coerceAtLeast(0)

    override fun getPreferredSize(): Dimension = getSpaceSize()
    override fun getMinimumSize(): Dimension = getSpaceSize()
    override fun getMaximumSize(): Dimension = getSpaceSize()

    private fun getSpaceSize(): Dimension = linearConstraints.width.coerceAtLeast(0) X linearConstraints.height.coerceAtLeast(0)
}