package dev.itssho.module.qpay.module.common.domain.usecase

import dev.itssho.module.hierarchy.storage.ValueStorage.StrList
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.common.domain.storage.UninitializedConstValueException

class GetModuleNameUseCase(
	private val fullyEditableValueStorage: FullyEditableValueStorage,
) {

	@Throws(UninitializedConstValueException::class)
	operator fun invoke() : List<String> =
		fullyEditableValueStorage.getList(StrList.MODULE_NAME) ?: throw UninitializedConstValueException(StrList.MODULE_NAME)
}