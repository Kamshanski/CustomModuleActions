package dev.itssho.module.service.action.module.domain.usecase

import dev.itssho.module.service.action.module.domain.entity.Script
import dev.itssho.module.service.action.module.domain.repository.ScriptRepository

class GetScriptsUseCase(private val scriptRepository: ScriptRepository) {

	operator fun invoke(): List<Script> =
		scriptRepository.get()
}