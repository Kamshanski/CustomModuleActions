package dev.itssho.module.hierarchy.importing

import dev.itssho.module.hierarchy.handler.HierarchyProcessor
import dev.itssho.module.hierarchy.initializer.HierarchyInitializer
import dev.itssho.module.hierarchy.initializer.ValuesInitializer
import dev.itssho.module.hierarchy.name.NameHandler

data class ModuleAction(
	val name: String,
	val nameHandler: NameHandler,
	val hierarchyInitializer: HierarchyInitializer,
	val valuesInitializer: ValuesInitializer,
	val hierarchyProcessor: HierarchyProcessor,
)