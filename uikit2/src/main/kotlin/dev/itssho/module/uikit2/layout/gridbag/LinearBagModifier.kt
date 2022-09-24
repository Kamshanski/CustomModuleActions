package dev.itssho.module.uikit2.layout.gridbag

import dev.itssho.module.uikit2.modify.Modifier
import dev.itssho.module.uikit2.modify.modification.*
import dev.itssho.module.component.value.Gravity
import dev.itssho.module.component.value.MATCH_PARENT
import dev.itssho.module.component.value.Orientation
import java.awt.Insets
import java.awt.GridBagConstraints as GBC

fun Modifier.toGridBagConstraints(orientation: Orientation): GBC {
    fun GBC.modify(mod: Modification) {
        when (mod) {
            is FillHeightModification      -> {
                fill = defineFill(widthFill = getWidthFill(fill), heightFill = true)
                weighty = mod.weight.toDouble()
            }
            is FillWidthModification       -> {
                fill = defineFill(widthFill = true, heightFill = getHeightFill(fill))
                weightx = mod.weight.toDouble()
            }

//            is HeightModification          -> {
//                when (mod.height) {
//                    MATCH_PARENT -> fill = defineFill(widthFill = getWidthFill(fill), heightFill = true)
//                    else         -> fill = defineFill(widthFill = getWidthFill(fill), heightFill = false)
//                }
//            }
//            is WidthModification           -> {
//                when (mod.width) {
//                    MATCH_PARENT -> fill = defineFill(widthFill = true, heightFill = getHeightFill(fill))
//                    else         -> fill = defineFill(widthFill = false, heightFill = getHeightFill(fill))
//                }
//            }

            is WeightModification          -> {
                when (orientation) {
                    Orientation.HORIZONTAL -> weightx = mod.weight.toDouble()
                    Orientation.VERTICAL   -> weighty = mod.weight.toDouble()
                }
            }

            is MarginBottomModification    -> insets = insets.copy(bottom = mod.margin)
            is MarginEndModification       -> insets = insets.copy(end = mod.margin)
            is MarginStartModification     -> insets = insets.copy(start = mod.margin)
            is MarginTopModification       -> insets = insets.copy(top = mod.margin)

            is AddMarginBottomModification -> insets = insets.add(bottom = mod.margin)
            is AddMarginEndModification    -> insets = insets.add(end = mod.margin)
            is AddMarginStartModification  -> insets = insets.add(start = mod.margin)
            is AddMarginTopModification    -> insets = insets.add(top = mod.margin)

            is GravityModification         -> anchor = when (mod.gravity) {
                Gravity.CENTER       -> GBC.CENTER
                Gravity.TOP          -> GBC.NORTH
                Gravity.BOTTOM       -> GBC.SOUTH
                Gravity.START        -> GBC.WEST
                Gravity.END          -> GBC.EAST
                Gravity.TOP_START    -> GBC.NORTHWEST
                Gravity.TOP_END      -> GBC.NORTHEAST
                Gravity.BOTTOM_START -> GBC.SOUTHEAST
                Gravity.BOTTOM_END   -> GBC.SOUTHWEST
            }

            else -> { /* skip */ }
        }
    }

    val modifiers = getAllModifiers()

    val constraints = GBC()
    for (mod in modifiers) {
        constraints.modify(mod)
    }

    return constraints
}


private inline fun getWidthFill(fill: Int): Boolean = fill == GBC.BOTH || fill == GBC.HORIZONTAL
private inline fun getHeightFill(fill: Int): Boolean = fill == GBC.BOTH || fill == GBC.VERTICAL

@Suppress("SimplifyBooleanWithConstants")
private inline fun defineFill(widthFill: Boolean, heightFill: Boolean): Int =
    when {
        widthFill == true && heightFill == true   -> GBC.BOTH
        widthFill == true && heightFill == false  -> GBC.HORIZONTAL
        widthFill == false && heightFill == true  -> GBC.VERTICAL
        widthFill == false && heightFill == false -> GBC.NONE
        else                                      -> throw IllegalStateException()
    }

private inline fun Insets.copy(top: Int? = null, bottom: Int? = null, start: Int? = null, end: Int? = null): Insets = Insets(
    top ?: this.top,
    start ?: this.left,
    bottom ?: this.bottom,
    end ?: this.right
)

private inline fun Insets.add(top: Int = 0, bottom: Int = 0, start: Int = 0, end: Int = 0): Insets = Insets(
    top + this.top,
    start + this.left,
    bottom + this.bottom,
    end + this.right
)