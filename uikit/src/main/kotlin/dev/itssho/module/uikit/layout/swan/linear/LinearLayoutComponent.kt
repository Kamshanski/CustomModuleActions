package dev.itssho.module.uikit.layout.swan.linear

import javax.swing.JComponent

private const val LINEAR_CONSTRAINTS_PROPERTY = "com.idayrus.layout.swan.linear.linearConstraints"
var JComponent.linearConstraints: LinearConstraints
    get() = getClientProperty(LINEAR_CONSTRAINTS_PROPERTY) as? LinearConstraints ?: LinearConstraints().also { this.linearConstraints = it }
    set(value) = putClientProperty(LINEAR_CONSTRAINTS_PROPERTY, value)

// Чтобы это работало, надо чтобы при изменении перерисоввался виджет
//var JComponent.gravity: LinearGravity
//    get() = linearConstraints.gravity
//    set(value) { linearConstraints.gravity = value }
//
//var JComponent.weight: Int
//    get() = linearConstraints.weight
//    set(value) { linearConstraints.weight = value }
//
//var JComponent.linearWidth: Int
//    get() = linearConstraints.width
//    set(value) { linearConstraints.width = value }
//
//var JComponent.linearHeight: Int
//    get() = linearConstraints.height
//    set(value) { linearConstraints.height = value }
//
//var JComponent.marginTop: Int
//    get() = linearConstraints.marginTop
//    set(value) { linearConstraints.marginTop = value }
//
//var JComponent.marginEnd: Int
//    get() = linearConstraints.marginEnd
//    set(value) { linearConstraints.marginEnd = value }
//
//var JComponent.marginBottom: Int
//    get() = linearConstraints.marginBottom
//    set(value) { linearConstraints.marginBottom = value }
//
//var JComponent.marginStart: Int
//    get() = linearConstraints.marginStart
//    set(value) { linearConstraints.marginStart = value }
//
//fun JComponent.setMarginHorizontal(margin: Int) {
//    marginEnd = margin
//    marginStart = margin
//}
//
//fun JComponent.setMarginVertical(margin: Int) {
//    marginTop = margin
//    marginBottom = margin
//}
//
//fun JComponent.setMargin(margin: Int) {
//    setMarginVertical(margin)
//    setMarginHorizontal(margin)
//}