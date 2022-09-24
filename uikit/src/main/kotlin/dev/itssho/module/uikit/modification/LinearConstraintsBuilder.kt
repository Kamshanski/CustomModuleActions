package dev.itssho.module.uikit.modification

import dev.itssho.module.uikit.layout.swan.SWAN_MATCH_PARENT
import dev.itssho.module.uikit.layout.swan.SWAN_WRAP_CONTENT
import dev.itssho.module.uikit.layout.swan.linear.LinearConstraints
import dev.itssho.module.uikit.layout.swan.linear.LinearGravity

class LinearConstraintsBuilder(
    var gravity: LinearGravity = LinearGravity.START,
    var width: Int = SWAN_WRAP_CONTENT,
    var height: Int = SWAN_WRAP_CONTENT,
    var weight: Int = 0,
    var marginTop: Int = 0,
    var marginEnd: Int = 0,
    var marginBottom: Int = 0,
    var marginStart: Int = 0,
) {

    fun build() = LinearConstraints(
        gravity = gravity,
        width = validateWidth(width),
        height = validateHeight(height),
        marginTop = validateMargin(marginTop),
        marginEnd = validateMargin(marginEnd),
        marginBottom = validateMargin(marginBottom),
        marginStart = validateMargin(marginStart),
        weight = validateWeight(weight),
    )

    private fun validateWidth(width: Int): Int {
        if (width == SWAN_MATCH_PARENT || width == SWAN_WRAP_CONTENT || width >= 0) {
            return width
        }
        throw IllegalArgumentException("Width=$width is wrong value")
    }

    private fun validateHeight(height: Int): Int {
        if (height == SWAN_MATCH_PARENT || height == SWAN_WRAP_CONTENT || height >= 0) {
            return height
        }
        throw IllegalArgumentException("Height=$height is wrong value")
    }

    private fun validateWeight(weight: Int): Int {
        if (weight >= 0) {
            return weight
        }
        throw IllegalArgumentException("Weight=$weight is wrong value")
    }

    private fun validateMargin(margin: Int): Int {
        if (margin >= 0) {
            return margin
        }
        throw IllegalArgumentException("Margin={top=$marginTop, bottom=$marginBottom, start=$marginStart, end=$marginEnd} is wrong value")
    }
}