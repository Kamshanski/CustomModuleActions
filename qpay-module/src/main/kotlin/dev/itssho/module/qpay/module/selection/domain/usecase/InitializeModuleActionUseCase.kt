package dev.itssho.module.qpay.module.selection.domain.usecase

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.storage.MutableValueStorage

class InitializeModuleActionUseCase(
	private val valueStorage: MutableValueStorage,
) {

	operator fun invoke(moduleAction: ModuleAction) {
		val initializer = moduleAction.valuesInitializer
		initializer.initialize(valueStorage)
	}
}