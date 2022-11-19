package dev.itssho.module.service.action.module.domain.usecase

import dev.itssho.module.service.action.module.domain.repository.ScriptRepository

class ReloadScriptUseCase(private val scriptRepository: ScriptRepository) {

	operator fun invoke(path: String) {
		val pathList = listOf(path)
		scriptRepository.load(pathList)
	}
}