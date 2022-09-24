package dev.itssho.module.qpay.module.common.domain.usecase

import dev.itssho.module.hierarchy.storage.StringListKey
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage

@Deprecated("Хз зачем это может быть нужно")
class SetProjectPathUseCase(
	private val fullyEditableValueStorage: FullyEditableValueStorage,
) {

	@Deprecated("Хз зачем это может быть нужно")
	operator fun invoke(path: List<String>) {
		fullyEditableValueStorage.forcePut(StringListKey.PROJECT_PATH, path)
	}
}