package dev.itssho.module.uikit2.dsl

import com.intellij.ui.components.JBScrollPane
import com.intellij.util.io.DigestUtil.random
import dev.itssho.module.uikit2.component.XLinearPanel
import dev.itssho.module.uikit2.modify.Modifier
import dev.itssho.module.uikit2.modify.modification.fillMaxHeight
import dev.itssho.module.uikit2.modify.modification.fillMaxWidth
import dev.itssho.module.uikit2.util.swing.addTo
import dev.itssho.module.component.value.Orientation
import java.awt.Color

fun panel(orientation: Orientation = Orientation.VERTICAL, panelBuilder: XLinearPanel.() -> Unit): XLinearPanel =
    XLinearPanel(orientation)
        .apply {
            background = Color(random.nextInt())
            panelBuilder()
        }

// TODO сделать по умолчанию WRAP_CONTENT вместо MATCH_PARENT
fun XLinearPanel.row(modifier: Modifier = Modifier, rowBuilder: XLinearPanel.() -> Unit) =
    XLinearPanel(Orientation.HORIZONTAL)
        .apply {
        background = Color(random.nextInt())
            rowBuilder()
        }
        .addTo(this, Modifier.fillMaxWidth().then(modifier))

// TODO сделать по умолчанию WRAP_CONTENT вместо MATCH_PARENT
fun XLinearPanel.column(modifier: Modifier = Modifier, rowBuilder: XLinearPanel.() -> Unit) = XLinearPanel(Orientation.VERTICAL)
        .apply {
        background = Color(random.nextInt())
            rowBuilder()
        }
        .addTo(this, Modifier.fillMaxHeight().then(modifier))

fun XLinearPanel.scrollRow(modifier: Modifier = Modifier, builder: XLinearPanel.() -> Unit) = JBScrollPane().also { scroll ->
    val contentPanel = panel(Orientation.HORIZONTAL, builder)
    scroll.setViewportView(contentPanel)
    scroll.addTo(this, modifier)
}

fun XLinearPanel.scrollColumn(modifier: Modifier = Modifier, builder: XLinearPanel.() -> Unit) = JBScrollPane().also { scroll ->
    val contentPanel = panel(Orientation.HORIZONTAL, builder)
    scroll.setViewportView(contentPanel)
    scroll.addTo(this, modifier)
}

// TODO сделать по умолчанию WRAP_CONTENT вместо MATCH_PARENT
fun XLinearPanel.column(modifier: Modifier = Modifier, isScrollable: Boolean, rowBuilder: XLinearPanel.() -> Unit) = XLinearPanel(Orientation.VERTICAL)
    .also { panel ->
        background = Color(random.nextInt())
        panel.rowBuilder()
        if (isScrollable) {
            JBScrollPane().also { pane ->
                pane.setViewportView(panel)
                pane.addTo(this, Modifier.fillMaxHeight().then(modifier))
            }
        } else {
            panel.addTo(this, Modifier.fillMaxHeight().then(modifier))
        }
    }

