package dev.itssho.module.hierarchy.importing

import dev.itssho.module.hierarchy.handler.HierarchyProcessor
import dev.itssho.module.hierarchy.initializer.HierarchyInitializer
import dev.itssho.module.hierarchy.initializer.ValuesInitializer
import dev.itssho.module.hierarchy.name.NameHandler

class OneShotModuleAction(
	name: String,
	override val nameHandler: NameHandler,
	override val hierarchyInitializer: HierarchyInitializer,
	override val valuesInitializer: ValuesInitializer,
	override val hierarchyProcessor: HierarchyProcessor,
) : ModuleAction(name)