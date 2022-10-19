package dev.itssho.module.qpay.module.common.domain.usecase

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.qpay.module.common.domain.repository.ModuleActionRepository

class LoadModuleActionUseCase(
	private val moduleActionRepository: ModuleActionRepository,
) {

	@Throws(IllegalStateException::class)
	suspend operator fun invoke(): ModuleAction =
		moduleActionRepository.get()
}