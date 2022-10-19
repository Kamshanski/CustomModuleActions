package dev.itssho.module.qpay.module.structure.domain.usecase

import dev.itssho.module.hierarchy.initializer.ValuesInitializer
import dev.itssho.module.hierarchy.storage.MutableValueStorage

class InitializeHierarchyUseCase(
	private val valuesInitializer: ValuesInitializer,
	private val valueStorage: MutableValueStorage,
) {

	operator fun invoke() {
		valuesInitializer.initialize(valueStorage)
	}
}