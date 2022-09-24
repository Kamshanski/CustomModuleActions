package dev.itssho.module.uikit2.modify.modification

import dev.itssho.module.uikit2.modify.Modifier
import dev.itssho.module.component.value.Gravity

fun Modifier.gravity(gravity: Gravity): Modifier = then(GravityModification(gravity))

data class GravityModification(val gravity: Gravity) : Modification