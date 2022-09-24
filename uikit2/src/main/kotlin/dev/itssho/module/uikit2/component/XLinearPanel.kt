package dev.itssho.module.uikit2.component

import dev.itssho.module.component.value.Orientation
import dev.itssho.module.uikit2.layout.gridbag.LinearBagLayout
import dev.itssho.module.uikit2.util.swing.addOnContainerChangedListener
import dev.itssho.module.uikit2.util.swing.addOnResizedListener
import dev.itssho.module.uikit2.util.swing.addOnShownListener
import java.awt.LayoutManager
import javax.swing.JPanel

class XLinearPanel(val orientation: Orientation) : JPanel(defineLayout(orientation)) {

    init {
        addOnShownListener { updateUI() }
        addOnResizedListener { updateUI() }
        addOnContainerChangedListener { updateUI() }
    }

    companion object {

        private fun defineLayout(orientation: Orientation): LayoutManager {
            return LinearBagLayout(orientation)
        }
    }
}