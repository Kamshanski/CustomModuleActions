package dev.itssho.module.uikit2.dsl.text

import dev.itssho.module.uikit2.component.XLinearPanel
import dev.itssho.module.uikit2.component.text.XTextField
import dev.itssho.module.uikit2.modify.Modifier
import dev.itssho.module.uikit2.util.swing.addTo
import javax.swing.Icon
import javax.swing.JFormattedTextField
import javax.swing.text.Document

fun XLinearPanel.textField(
    modifier: Modifier = Modifier,
    text: String,
    icon: Icon? = null,
    isEditable: Boolean = true,
    listener: (String) -> Unit = {},
): XTextField = XTextField(text)
    .apply {
        this.isEditable = isEditable
        addTextChangeListener(listener)
    }
    .addTo(this, modifier)

@Deprecated("Not implemented")
fun XLinearPanel.formattedTextField(
    modifier: Modifier = Modifier,
    text: String,
    formatter: JFormattedTextField.AbstractFormatter,
    icon: Icon? = null,
    listener: (String) -> Unit = {},
): JFormattedTextField = JFormattedTextField(formatter)
    .apply {
        this.text = text
        // TODO addTextChangeListener(listener)
    }
    .addTo(this, modifier)

private val Document.fullText: String
    get() =
        getText(0, length)