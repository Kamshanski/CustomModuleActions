package dev.itssho.module.uikit2.layout.gridbag

import dev.itssho.module.uikit2.modify.Modifier
import dev.itssho.module.component.value.Orientation
import java.awt.Component
import java.awt.GridBagConstraints
import java.awt.GridBagLayout

class LinearBagLayout(private val orientation: Orientation) : GridBagLayout() {

    private var counter: Int = 0

    override fun addLayoutComponent(name: String?, comp: Component?) {
        throw NotImplementedError("Not implemented here. Use another addLayoutComponent(comp: Component?, constraints: Any?)")
    }

    override fun addLayoutComponent(comp: Component?, constraints: Any?) {
        val gridBagConstraints: GridBagConstraints = when(constraints) {
            is GridBagConstraints -> constraints
            is Modifier           -> constraints.toGridBagConstraints(orientation)
            else                  -> GridBagConstraints()
        }

        when (orientation) {
            Orientation.HORIZONTAL -> {
                gridBagConstraints.gridx = counter
                gridBagConstraints.gridy = 0
            }
            Orientation.VERTICAL   -> {
                gridBagConstraints.gridy = counter
                gridBagConstraints.gridx = 0
            }
        }
        gridBagConstraints.gridheight = 1
        gridBagConstraints.gridwidth = 1
        counter += 1

        super.addLayoutComponent(comp, gridBagConstraints)
    }
}