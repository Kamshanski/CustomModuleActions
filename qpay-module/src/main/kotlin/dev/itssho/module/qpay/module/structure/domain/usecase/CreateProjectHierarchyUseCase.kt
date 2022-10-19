package dev.itssho.module.qpay.module.structure.domain.usecase

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.initializer.HierarchyInitializer
import dev.itssho.module.hierarchy.storage.MutableValueStorage

class CreateProjectHierarchyUseCase(
	private val hierarchyInitializer: HierarchyInitializer,
	private val valueStorage: MutableValueStorage,
) {

	operator fun invoke(): HierarchyObject {
	    return hierarchyInitializer.initialize(valueStorage)
	}
}