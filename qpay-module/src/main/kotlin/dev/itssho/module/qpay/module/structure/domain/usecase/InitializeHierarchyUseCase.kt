package dev.itssho.module.qpay.module.structure.domain.usecase

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.storage.ValueStorage

class InitializeHierarchyUseCase(
	private val moduleAction: ModuleAction,
	private val valueStorage: ValueStorage,
) {

	operator fun invoke(): HierarchyObject {
		val hierarchyInitializer = moduleAction.hierarchyInitializer
		return hierarchyInitializer.initialize(valueStorage)
	}
}