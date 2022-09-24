package dev.itssho.module.uikit2.modify

import dev.itssho.module.uikit2.modify.modification.Modification

interface Modifier {

    fun then(modification: Modification) : Modifier

    fun then(modifier: Modifier) : Modifier

    fun getAllModifiers(): List<Modification>

    companion object : Modifier {

        override fun then(modification: Modification) : Modifier {
            return ModifiersCollection().also { it.then(modification) }
        }

        override fun then(modifier: Modifier): Modifier {
            return ModifiersCollection().also { it.then(modifier) }
        }

        override fun getAllModifiers(): List<Modification> = emptyList()
    }

    private class ModifiersCollection : Modifier {

        private val items = mutableListOf<Modification>()

        override fun then(modification: Modification) : Modifier = apply {
            items.add(modification)
        }
        override fun then(modifier: Modifier) : Modifier = apply {
            items.addAll(modifier.getAllModifiers())
        }

        override fun getAllModifiers(): List<Modification> = items
    }
}