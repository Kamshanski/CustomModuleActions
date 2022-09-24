package dev.itssho.module.uikit2.util.swing

import dev.itssho.module.uikit2.modify.Modifier
import java.awt.Component
import java.awt.Container

fun <T : Component> T.addTo(container: Container, modifier: Modifier?): T = apply {
    container.add(this, modifier)
}