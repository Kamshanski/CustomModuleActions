package dev.itssho.module.uikit2.dsl

import dev.itssho.module.uikit2.component.XLinearPanel
import dev.itssho.module.uikit2.modify.Modifier
import dev.itssho.module.uikit2.util.swing.addTo
import javax.swing.Icon
import javax.swing.JButton

fun XLinearPanel.button(modifier: Modifier = Modifier, text: String = "", icon: Icon? = null, action: JButton.() -> Unit): JButton =
    JButton(text, icon)
        .apply {
            addActionListener { action() }
        }
        .addTo(this, modifier)