package dev.itssho.module.uikit.dsl.select

import dev.itssho.module.uikit.component.ButtonCollection
import dev.itssho.module.uikit.component.ButtonCollectionBuilder
import dev.itssho.module.uikit.component.ButtonCollectionBuilderImpl
import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.modification.Modifier
import dev.itssho.module.uikit.extensions.addTo
import dev.itssho.module.uikit.layout.swan.linear.linearConstraints
import javax.swing.JRadioButton

fun JXLinearPanel.radioButton(modifier: Modifier = Modifier(), text: String, selected: Boolean, listener: ((Boolean) -> Unit)? = null): JRadioButton =
    JRadioButton(text)
        .apply { linearConstraints = modifier.assemble() }
        .also { btn ->
            if (listener == null) return@also
            btn.addActionListener { listener.invoke(btn.isSelected) }
        }
        .addTo(this)

fun JXLinearPanel.singleSelectionButtonsGroup(buttonGroupComposer: ButtonCollectionBuilder.() -> Unit): ButtonCollection =
    ButtonCollectionBuilderImpl().apply { buttonGroupComposer() }.build()