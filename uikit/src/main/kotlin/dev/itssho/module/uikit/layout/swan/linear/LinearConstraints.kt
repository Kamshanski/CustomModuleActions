package dev.itssho.module.uikit.layout.swan.linear

import dev.itssho.module.uikit.layout.swan.SWAN_WRAP_CONTENT
import java.io.Serializable

data class LinearConstraints(
    var gravity: LinearGravity = LinearGravity.START,
    var width: Int = SWAN_WRAP_CONTENT,
    var height: Int = SWAN_WRAP_CONTENT,
    var calculatedWidth: Int = 0,
    var calculatedHeight: Int = 0,
    var marginTop: Int = 0,
    var marginEnd: Int = 0,
    var marginBottom: Int = 0,
    var marginStart: Int = 0,
    var weight: Int = 0,
    private var marginValue: Int = 0,
    private var weightValue: Double = 0.0,
) : Serializable {

    var margin: Int
        get() = marginValue
        set(value) {
            marginTop = value
            marginEnd = value
            marginBottom = value
            marginStart = value
            marginValue = value
        }

    private var _weight: Int = 0

    init {
        this.weight = weight
    }

    fun reset() {
        gravity = LinearGravity.START
        width = SWAN_WRAP_CONTENT
        height = SWAN_WRAP_CONTENT
        marginValue = 0
        weightValue = 0.0
    }

    companion object {}
}