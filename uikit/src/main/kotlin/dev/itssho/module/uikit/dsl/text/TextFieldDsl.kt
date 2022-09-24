package dev.itssho.module.uikit.dsl.text

import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.component.text.JXTextField
import dev.itssho.module.uikit.modification.Modifier
import dev.itssho.module.uikit.extensions.addTo
import dev.itssho.module.uikit.layout.swan.linear.linearConstraints
import javax.swing.Icon
import javax.swing.JFormattedTextField
import javax.swing.text.Document

fun JXLinearPanel.textField(
    modifier: Modifier = Modifier(),
    text: String,
    icon: Icon? = null,
    isEditable: Boolean = true,
    listener: (String) -> Unit = {},
): JXTextField =
    JXTextField(text)
        .apply {
            linearConstraints = modifier.assemble()
            this.isEditable = isEditable
            addTextChangeListener(listener)
        }
        .addTo(this)

@Deprecated("Not implemented")
fun JXLinearPanel.formattedTextField(
    modifier: Modifier = Modifier(),
    text: String,
    formatter: JFormattedTextField.AbstractFormatter,
    icon: Icon? = null,
    listener: (String) -> Unit = {},
): JFormattedTextField =
    JFormattedTextField(formatter)
        .apply {
            linearConstraints = modifier.assemble()
            this.text = text
            // TODO addTextChangeListener(listener)
        }

        .addTo(this)

private val Document.fullText: String
    get() =
        getText(0, length)