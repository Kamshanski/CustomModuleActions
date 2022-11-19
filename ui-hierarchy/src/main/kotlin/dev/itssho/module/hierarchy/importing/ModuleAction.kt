package dev.itssho.module.hierarchy.importing

import dev.itssho.module.hierarchy.handler.HierarchyProcessor
import dev.itssho.module.hierarchy.initializer.HierarchyInitializer
import dev.itssho.module.hierarchy.initializer.ValuesInitializer
import dev.itssho.module.hierarchy.name.NameHandler

sealed class ModuleAction(
	val name: String,
) {
	abstract val nameHandler: NameHandler
	abstract val hierarchyInitializer: HierarchyInitializer
	abstract val valuesInitializer: ValuesInitializer
	abstract val hierarchyProcessor: HierarchyProcessor
}