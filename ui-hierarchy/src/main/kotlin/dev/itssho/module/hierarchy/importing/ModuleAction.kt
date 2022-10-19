package dev.itssho.module.hierarchy.importing

import dev.itssho.module.hierarchy.handler.HierarchyProcessor
import dev.itssho.module.hierarchy.initializer.HierarchyInitializer
import dev.itssho.module.hierarchy.initializer.ValuesInitializer

data class ModuleAction(
	val name: String,
	val hierarchyInitializer: HierarchyInitializer,
	val valuesInitializer: ValuesInitializer,
	val hierarchyProcessor: HierarchyProcessor,
)