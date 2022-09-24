package dev.itssho.module.uikit.dsl.label

import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.component.text.JXLabel
import dev.itssho.module.uikit.component.text.TextAlignment
import dev.itssho.module.uikit.modification.Modifier
import dev.itssho.module.uikit.extensions.addTo
import dev.itssho.module.uikit.layout.swan.linear.linearConstraints

fun JXLinearPanel.label(modifier: Modifier = Modifier(), text: String = "", textAlignment: TextAlignment = TextAlignment.LEFT): JXLabel =
    JXLabel(text, textAlignment = textAlignment)
        .apply {
            linearConstraints = modifier.assemble()
        }
        .addTo(this)
