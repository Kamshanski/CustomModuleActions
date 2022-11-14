package dev.itssho.module.qpay.module.selection.domain.usecase

import dev.itssho.module.qpay.module.selection.domain.entity.Script
import dev.itssho.module.qpay.module.selection.domain.repository.ScriptRepository

class GetScriptsUseCase(private val scriptRepository: ScriptRepository) {

	operator fun invoke(): List<Script> =
		scriptRepository.get()
}
