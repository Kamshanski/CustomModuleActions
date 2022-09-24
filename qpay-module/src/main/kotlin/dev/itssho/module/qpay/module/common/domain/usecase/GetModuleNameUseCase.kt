package dev.itssho.module.qpay.module.common.domain.usecase

import dev.itssho.module.hierarchy.storage.StringListKey
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.common.domain.storage.UninitializedConstValueException

class GetModuleNameUseCase(
	private val fullyEditableValueStorage: FullyEditableValueStorage,
) {

	@Throws(UninitializedConstValueException::class)
	operator fun invoke() : List<String> =
		fullyEditableValueStorage.getList(StringListKey.MODULE_NAME) ?: throw UninitializedConstValueException(StringListKey.MODULE_NAME)
}