package dev.itssho.module.uikit.dsl.separator

import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.extensions.addTo
import dev.itssho.module.uikit.layout.swan.linear.LinearOrientation
import dev.itssho.module.uikit.layout.swan.linear.LinearOrientation.Horizontal
import dev.itssho.module.uikit.layout.swan.linear.LinearOrientation.Vertical
import dev.itssho.module.uikit.layout.swan.linear.linearConstraints
import dev.itssho.module.uikit.modification.*
import java.awt.Color
import javax.swing.JComponent
import javax.swing.JLabel

private fun JXLinearPanel.divider(color: Color, linearOrientation: LinearOrientation, modifier: Modifier = Modifier()): JComponent = JLabel().apply {
    val defaultModifier = when (linearOrientation) {
        Horizontal -> Modifier.fillMaxWidth().height(1)
        Vertical   -> Modifier.fillMaxHeight().width(1)
    }
    background = color
    foreground = Color(0,0,0,0)
    isOpaque = true
    linearConstraints = defaultModifier.then(modifier).assemble()
}.addTo(this)


fun JXLinearPanel.hDivider(color: Color, modifier: Modifier = Modifier()): JComponent =
    divider(color, Horizontal, modifier)

fun JXLinearPanel.vDivider(color: Color, modifier: Modifier = Modifier()): JComponent =
    divider(color, Vertical, modifier)