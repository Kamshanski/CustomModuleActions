package dev.itssho.module.qpay.module.common.domain.usecase

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.qpay.module.common.domain.repository.ModuleActionRepository

@Deprecated("Старая реализация")
class GetModuleActionUseCase(
	private val moduleActionRepository: ModuleActionRepository,
) {

	@Throws(IllegalStateException::class)
	operator fun invoke(): ModuleAction =
		moduleActionRepository.getCached()
}