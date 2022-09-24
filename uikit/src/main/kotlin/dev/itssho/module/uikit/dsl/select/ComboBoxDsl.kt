package dev.itssho.module.uikit.dsl.select

import com.intellij.openapi.ui.ComboBox
import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.modification.Modifier
import dev.itssho.module.uikit.extensions.addTo
import dev.itssho.module.uikit.layout.swan.linear.linearConstraints
import java.awt.event.ItemEvent

inline fun <reified T : Any> JXLinearPanel.comboBox(
    modifier: Modifier = Modifier(),
    items: Array<T>,
    noinline listener: ((T) -> Unit)? = null,
): ComboBox<T> =
    ComboBox(items)
        .apply { linearConstraints = modifier.assemble() }
        .also { btn ->
            if (listener == null) return@also
            btn.addItemListener {
                if (it.stateChange == ItemEvent.SELECTED) {
                    listener(it.item as T)
                }
            }
        }
        .addTo(this)