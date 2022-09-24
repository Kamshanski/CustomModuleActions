package dev.itssho.module.uikit.modification

fun Modifier.weight(weight: Int): Modifier = then(WeightModification(weight))

data class WeightModification(val weight: Int) : Modification {

    override fun modify(constraints: LinearConstraintsBuilder): LinearConstraintsBuilder =
        constraints.also { it.weight = weight }
}