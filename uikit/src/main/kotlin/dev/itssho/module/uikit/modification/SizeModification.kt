package dev.itssho.module.uikit.modification

import dev.itssho.module.uikit.layout.swan.SWAN_MATCH_PARENT
import dev.itssho.module.uikit.layout.swan.SWAN_SELF_COMPUTED
import dev.itssho.module.uikit.layout.swan.SWAN_WRAP_CONTENT

fun Modifier.width(width: Int): Modifier = then(WidthModification(width))
fun Modifier.height(height: Int): Modifier = then(HeightModification(height))

fun Modifier.size(width: Int, height: Int): Modifier =
    width(width).height(height)

fun Modifier.fillMaxWidth(): Modifier = width(SWAN_MATCH_PARENT)
fun Modifier.wrapWidth(): Modifier = width(SWAN_WRAP_CONTENT)
fun Modifier.weightedWidth(): Modifier = width(SWAN_SELF_COMPUTED)

fun Modifier.fillMaxHeight(): Modifier = height(SWAN_MATCH_PARENT)
fun Modifier.wrapHeight(): Modifier = height(SWAN_WRAP_CONTENT)
fun Modifier.weightedHeight(): Modifier = height(SWAN_SELF_COMPUTED)

data class WidthModification(val width: Int) : Modification {

    override fun modify(constraints: LinearConstraintsBuilder): LinearConstraintsBuilder =
        constraints.also { it.width = width }
}

data class HeightModification(val height: Int) : Modification {

    override fun modify(constraints: LinearConstraintsBuilder): LinearConstraintsBuilder =
        constraints.also { it.height = height }
}