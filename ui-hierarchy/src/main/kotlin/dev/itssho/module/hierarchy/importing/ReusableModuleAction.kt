package dev.itssho.module.hierarchy.importing

import dev.itssho.module.hierarchy.handler.HierarchyProcessor
import dev.itssho.module.hierarchy.initializer.HierarchyInitializer
import dev.itssho.module.hierarchy.initializer.ValuesInitializer
import dev.itssho.module.hierarchy.name.NameHandler

class ReusableModuleAction(
	name: String,
	nameHandler: () -> NameHandler,
	hierarchyInitializer: () -> HierarchyInitializer,
	valuesInitializer: () -> ValuesInitializer,
	hierarchyProcessor: () -> HierarchyProcessor,
) : ModuleAction(name), ReusableAction {

	val nameHandlerFactory: () -> NameHandler = nameHandler
	val hierarchyInitializerFactory: () -> HierarchyInitializer = hierarchyInitializer
	val valuesInitializerFactory: () -> ValuesInitializer = valuesInitializer
	val hierarchyProcessorFactory: () -> HierarchyProcessor = hierarchyProcessor

	override var nameHandler: NameHandler = nameHandlerFactory()
		private set
	override var hierarchyInitializer: HierarchyInitializer = hierarchyInitializerFactory()
		private set
	override var valuesInitializer: ValuesInitializer = valuesInitializerFactory()
		private set
	override var hierarchyProcessor: HierarchyProcessor = hierarchyProcessorFactory()
		private set

	override fun recycle() {
		nameHandler = nameHandlerFactory()
		hierarchyInitializer = hierarchyInitializerFactory()
		valuesInitializer = valuesInitializerFactory()
		hierarchyProcessor = hierarchyProcessorFactory()
	}
}