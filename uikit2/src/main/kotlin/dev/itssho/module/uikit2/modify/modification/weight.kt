package dev.itssho.module.uikit2.modify.modification

import dev.itssho.module.uikit2.modify.Modifier

fun Modifier.weight(weight: Int): Modifier = then(WeightModification(weight))

data class WeightModification(val weight: Int) : Modification