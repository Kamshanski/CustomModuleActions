package dev.itssho.module.uikit.dsl.button

import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.modification.Modifier
import dev.itssho.module.uikit.extensions.addTo
import dev.itssho.module.uikit.layout.swan.linear.linearConstraints
import javax.swing.Icon
import javax.swing.JButton

fun JXLinearPanel.button(modifier: Modifier = Modifier(), text: String = "", icon: Icon? = null, action: JButton.() -> Unit): JButton =
    JButton(text, icon)
        .apply {
            linearConstraints = modifier.assemble()
            addActionListener { action() }
        }
        .addTo(this)