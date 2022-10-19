package dev.itssho.module.qpay.module.common.domain.usecase

import dev.itssho.module.hierarchy.storage.ValueStorage.StrList
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage

class SetModuleNameUseCase(
	private val fullyEditableValueStorage: FullyEditableValueStorage,
) {

	operator fun invoke(name: List<String>) {
		fullyEditableValueStorage.forcePut(StrList.MODULE_NAME, name)
	}
}