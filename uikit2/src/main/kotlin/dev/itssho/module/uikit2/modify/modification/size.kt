package dev.itssho.module.uikit2.modify.modification

import dev.itssho.module.uikit2.modify.Modifier
import dev.itssho.module.component.value.MATCH_PARENT
import dev.itssho.module.component.value.SELF_COMPUTED
import dev.itssho.module.component.value.WRAP_CONTENT

fun Modifier.width(width: Int): Modifier = then(WidthModification(width))
fun Modifier.height(height: Int): Modifier = then(HeightModification(height))

fun Modifier.size(width: Int, height: Int): Modifier =
    width(width).height(height)

fun Modifier.fillMaxWidth(): Modifier = width(MATCH_PARENT)
fun Modifier.wrapWidth(): Modifier = width(WRAP_CONTENT)
fun Modifier.weightedWidth(): Modifier = width(SELF_COMPUTED)

fun Modifier.fillMaxHeight(): Modifier = height(MATCH_PARENT)
fun Modifier.wrapHeight(): Modifier = height(WRAP_CONTENT)
fun Modifier.weightedHeight(): Modifier = height(SELF_COMPUTED)

data class WidthModification(val width: Int) : Modification

data class HeightModification(val height: Int) : Modification

fun Modifier.fillWidth(weight: Int = 0): Modifier = then(FillWidthModification(weight))
fun Modifier.fillHeight(weight: Int = 0): Modifier = then(FillHeightModification(weight))

data class FillWidthModification(val weight: Int) : Modification
data class FillHeightModification(val weight: Int) : Modification