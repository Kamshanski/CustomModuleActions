package dev.itssho.module.uikit.modification

import dev.itssho.module.uikit.layout.swan.linear.LinearGravity

fun Modifier.gravity(gravity: LinearGravity): Modifier = then(GravityModification(gravity))

data class GravityModification(val gravity: LinearGravity) : Modification {

    override fun modify(constraints: LinearConstraintsBuilder): LinearConstraintsBuilder =
        constraints.also { it.gravity = gravity }
}