package dev.itssho.module.qpay.module.common.domain.usecase

import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage

class SetModuleNameUseCase(
	private val fullyEditableValueStorage: FullyEditableValueStorage,
) {

	operator fun invoke(name: String) {
		fullyEditableValueStorage.forcePut(ValueStorage.Str.MODULE_NAME, name)
	}
}