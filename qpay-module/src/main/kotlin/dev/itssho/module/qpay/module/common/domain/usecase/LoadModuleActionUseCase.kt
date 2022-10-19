package dev.itssho.module.qpay.module.common.domain.usecase

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.qpay.module.common.domain.repository.ModuleActionRepository
import java.nio.file.InvalidPathException

class LoadModuleActionUseCase(
	private val moduleActionRepository: ModuleActionRepository,
	private val getSettingsUseCase: GetSettingsUseCase,
) {

	@Throws(IllegalStateException::class, InvalidPathException::class)
	suspend operator fun invoke(): ModuleAction {
		val settings = getSettingsUseCase()
		val scriptPath = settings.scriptPath
		val moduleAction = moduleActionRepository.get(scriptPath)
		return moduleAction
	}
}