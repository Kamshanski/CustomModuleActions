package dev.itssho.module.uikit2.dsl

import dev.itssho.module.uikit2.component.XLinearPanel
import dev.itssho.module.uikit2.component.text.XLabel
import dev.itssho.module.uikit2.modify.Modifier
import dev.itssho.module.uikit2.util.swing.addTo
import dev.itssho.module.component.value.Gravity

fun XLinearPanel.label(modifier: Modifier = Modifier, text: String = "", textAlignment: Gravity = Gravity.START): XLabel =
    XLabel(text, textAlignment = textAlignment)
        .apply {

        }
        .addTo(this, modifier)
