package dev.itssho.module.uikit2.dsl.select

import com.intellij.openapi.ui.ComboBox
import dev.itssho.module.uikit2.component.XLinearPanel
import dev.itssho.module.uikit2.modify.Modifier
import dev.itssho.module.uikit2.util.swing.addTo
import java.awt.event.ItemEvent

inline fun <reified T : Any> XLinearPanel.comboBox(
    modifier: Modifier = Modifier,
    items: Array<T>,
    noinline listener: ((T) -> Unit)? = null,
): ComboBox<T> =
    ComboBox(items)
        .also { btn ->
            if (listener != null) {
                btn.addItemListener {
                    if (it.stateChange == ItemEvent.SELECTED) {
                        listener(it.item as T)
                    }
                }
            }
        }
        .addTo(this, modifier)