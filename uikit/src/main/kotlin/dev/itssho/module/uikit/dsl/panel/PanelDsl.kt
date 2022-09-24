package dev.itssho.module.uikit.dsl.panel

import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.modification.Modifier
import dev.itssho.module.uikit.modification.fillMaxHeight
import dev.itssho.module.uikit.modification.fillMaxWidth
import dev.itssho.module.uikit.extensions.addTo
import dev.itssho.module.uikit.layout.swan.linear.linearConstraints
import dev.itssho.module.uikit.layout.v2.parameters.Orientation

fun panel(modifier: Modifier = Modifier(), panelBuilder: JXLinearPanel.() -> Unit): JXLinearPanel =
    JXLinearPanel(Orientation.VERTICAL)
        .apply {
            linearConstraints = Modifier.fillMaxHeight().fillMaxWidth().then(modifier).assemble()
//            background = Color(random.nextInt())
            panelBuilder()
        }

fun JXLinearPanel.row(modifier: Modifier = Modifier(), rowBuilder: JXLinearPanel.() -> Unit) =
    JXLinearPanel(Orientation.HORIZONTAL)
        .apply {
            linearConstraints = Modifier.fillMaxWidth().then(modifier).assemble()
//        background = Color(random.nextInt())
            rowBuilder()
        }
        .addTo(this)

fun JXLinearPanel.column(modifier: Modifier = Modifier(), rowBuilder: JXLinearPanel.() -> Unit) =
    JXLinearPanel(Orientation.VERTICAL)
        .apply {
            linearConstraints = Modifier.fillMaxHeight().then(modifier).assemble()
//        background = Color(random.nextInt())
            rowBuilder()
        }
        .addTo(this)

