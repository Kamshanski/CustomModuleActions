package dev.itssho.module.hierarchy.initializer

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.storage.ValueStorage

// Использовать для создания иерархии. Поле
interface HierarchyInitializer {

	fun initialize(valueStorage: ValueStorage): HierarchyObject
}
