package dev.itssho.module.uikit2.dsl

import dev.itssho.module.uikit2.component.XLinearPanel
import dev.itssho.module.uikit2.modify.Modifier
import dev.itssho.module.uikit2.modify.modification.fillMaxHeight
import dev.itssho.module.uikit2.modify.modification.fillMaxWidth
import dev.itssho.module.uikit2.util.swing.addTo
import dev.itssho.module.component.value.Orientation
import java.awt.Color
import javax.swing.JComponent
import javax.swing.JSeparator
import javax.swing.SwingConstants

private fun XLinearPanel.divider(color: Color, linearOrientation: Orientation, modifier: Modifier = Modifier): JComponent = JSeparator().also { separator ->
    val defaultModifier = when (linearOrientation) {
        Orientation.HORIZONTAL -> {
            separator.orientation = SwingConstants.HORIZONTAL
            Modifier.fillMaxWidth()
        }
        Orientation.VERTICAL   -> {
            separator.orientation = SwingConstants.VERTICAL
            Modifier.fillMaxHeight()
        }
    }
    separator.background = color
    separator.foreground = Color(0, 0, 0, 0)
    separator.isOpaque = true

    addTo(this, defaultModifier.then(modifier))
}



fun XLinearPanel.hDivider(color: Color, modifier: Modifier = Modifier): JComponent =
    divider(color, Orientation.HORIZONTAL, modifier)

fun XLinearPanel.vDivider(color: Color, modifier: Modifier = Modifier): JComponent =
    divider(color, Orientation.VERTICAL, modifier)