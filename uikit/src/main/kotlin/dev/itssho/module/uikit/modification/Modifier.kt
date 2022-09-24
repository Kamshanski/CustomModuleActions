package dev.itssho.module.uikit.modification

import dev.itssho.module.uikit.layout.swan.linear.LinearConstraints

interface Modifier : Modification {

    companion object : Modifier {

        operator fun invoke(): Modifier =
            ModifierCollection()

        override fun then(modifier: Modification): Modifier =
            ModifierCollection().then(modifier)

        override fun modify(constraints: LinearConstraintsBuilder): LinearConstraintsBuilder =
            constraints
    }

    private class ModifierCollection : Modifier {

        val items: MutableList<Modification> = mutableListOf()

        override fun then(modifier: Modification): Modifier = apply {
            items.add(modifier)
        }

        override fun modify(constraints: LinearConstraintsBuilder): LinearConstraintsBuilder {
            for (modifier in items) {
                modifier.modify(constraints)
            }
            return constraints
        }
    }

    fun then(modifier: Modification): Modifier

    fun assemble(): LinearConstraints = modify(LinearConstraintsBuilder()).build()
}