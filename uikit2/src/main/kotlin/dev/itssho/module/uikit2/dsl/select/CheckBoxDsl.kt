package dev.itssho.module.uikit2.dsl.select

import dev.itssho.module.uikit2.component.XLinearPanel
import dev.itssho.module.uikit2.modify.Modifier
import dev.itssho.module.uikit2.util.swing.addTo
import javax.swing.JCheckBox

fun XLinearPanel.checkBox(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean = false,
    listener: ((Boolean) -> Unit)? = null,
): JCheckBox =
    JCheckBox(text, isSelected)
        .also { btn ->
            if (listener == null) return@also
            btn.addActionListener { listener.invoke(btn.isSelected) }
        }
        .addTo(this, modifier)