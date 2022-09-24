package dev.itssho.module.uikit.dsl.separator

import dev.itssho.module.uikit.component.JXSpace
import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.modification.Modifier
import dev.itssho.module.uikit.modification.height
import dev.itssho.module.uikit.modification.width
import dev.itssho.module.uikit.extensions.addTo
import dev.itssho.module.uikit.layout.swan.linear.linearConstraints
import dev.itssho.module.uikit.util.requireNonNegative

fun JXLinearPanel.space(modifier: Modifier = Modifier()): JXSpace =
    JXSpace().apply {
        linearConstraints = modifier.assemble()
    }.addTo(this)

fun JXLinearPanel.hSpace(width: Int, modifier: Modifier = Modifier()): JXSpace =
    space(
        modifier.width(requireNonNegative(width))
    )

fun JXLinearPanel.vSpace(height: Int, modifier: Modifier = Modifier()): JXSpace =
    space(
        modifier.height(requireNonNegative(height))
    )