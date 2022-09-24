package dev.itssho.module.uikit.layout.swan.frame

import dev.itssho.module.uikit.layout.swan.SWAN_WRAP_CONTENT
import java.io.Serializable

data class FrameConstraints(
    var gravity: String = CENTER,
    var width: Int = SWAN_WRAP_CONTENT,
    var height: Int = SWAN_WRAP_CONTENT,
    var marginTop: Int = 0,
    var marginEnd: Int = 0,
    var marginBottom: Int = 0,
    var marginStart: Int = 0,
    private var marginValue: Int = 0,
) : Serializable {

    companion object {
        const val TOP_START = "top_start"
        const val TOP_END = "top_end"
        const val TOP_CENTER = "top_center"
        const val CENTER_START = "center_start"
        const val CENTER_END = "center_end"
        const val CENTER_CENTER = "center"
        const val BOTTOM_START = "bottom_start"
        const val BOTTOM_END = "bottom_end"
        const val BOTTOM_CENTER = "bottom_center"
        const val CENTER = "center"
    }

    var margin: Int
        get() = marginValue
        set(value) {
            marginTop = value
            marginEnd = value
            marginBottom = value
            marginStart = value
            marginValue = value
        }

    fun reset() {
        gravity = CENTER_CENTER
        width = SWAN_WRAP_CONTENT
        height = SWAN_WRAP_CONTENT
        margin = 0
    }
}