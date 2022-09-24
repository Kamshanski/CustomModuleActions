package dev.itssho.module.uikit2.util.swing

import java.awt.Component
import java.awt.Dimension
import java.lang.Integer.max

fun Dimension.copy(width: Int? = null, height: Int? = null): Dimension = Dimension(width ?: this.width, height ?: this.height)

val Component.preferredOrMinSize: Dimension
    get() {
        val preferred = preferredSize
        val minimum = minimumSize
        return Dimension(max(preferred.width, minimum.width), max(preferred.height, minimum.height))
    }

infix fun Int.x(height: Int) = Dimension(this, height)
infix fun Int.X(height: Int) = Dimension(this, height)

infix fun Dimension.plusWidth(dw: Int): Dimension = (width + dw) x height
infix fun Dimension.plusHeight(dw: Int): Dimension = (width + dw) x height

fun Dimension.plus(dw: Int = 0, dh: Int = 0): Dimension = (width + dw) x (height + dh)