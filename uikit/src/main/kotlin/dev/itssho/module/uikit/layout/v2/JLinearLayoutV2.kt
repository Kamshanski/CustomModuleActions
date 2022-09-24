package dev.itssho.module.uikit.layout.v2

import dev.itssho.module.uikit.layout.v2.parameters.Gravity
import dev.itssho.module.uikit.layout.v2.parameters.Orientation
import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import java.awt.LayoutManager
import javax.swing.JComponent

class JLinearLayoutV2(
    private val orientation: Orientation,
    private val gravity: Gravity,
) : LayoutManager {

    val children: MutableList<JComponent> = mutableListOf()

    override fun addLayoutComponent(name: String?, comp: Component?) {

    }

    override fun removeLayoutComponent(comp: Component?) {
        TODO("Not yet implemented")
    }

    override fun preferredLayoutSize(parent: Container?): Dimension {
        TODO("Not yet implemented")
    }

    override fun minimumLayoutSize(parent: Container?): Dimension {
        TODO("Not yet implemented")
    }

    override fun layoutContainer(parent: Container?) {
        TODO("Not yet implemented")
    }
}