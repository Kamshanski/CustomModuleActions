package dev.itssho.module.uikit.dsl.select

import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.modification.Modifier
import dev.itssho.module.uikit.extensions.addTo
import dev.itssho.module.uikit.layout.swan.linear.linearConstraints
import javax.swing.JCheckBox

fun JXLinearPanel.checkBox(
    modifier: Modifier = Modifier(),
    text: String,
    selected: Boolean = false,
    listener: ((Boolean) -> Unit)? = null,
): JCheckBox =
    JCheckBox(text, selected)
        .apply { linearConstraints = modifier.assemble() }
        .also { btn ->
            if (listener == null) return@also
            btn.addActionListener { listener.invoke(btn.isSelected) }
        }
        .addTo(this)