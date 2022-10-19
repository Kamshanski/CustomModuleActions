package dev.itssho.module.qpay.module.common.domain.usecase

import dev.itssho.module.hierarchy.storage.ValueStorage.StrList
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage

@Deprecated("Хз зачем это может быть нужно")
class SetProjectPathUseCase(
	private val fullyEditableValueStorage: FullyEditableValueStorage,
) {

	@Deprecated("Хз зачем это может быть нужно")
	operator fun invoke(path: List<String>) {
		fullyEditableValueStorage.forcePut(StrList.PROJECT_PATH, path)
	}
}