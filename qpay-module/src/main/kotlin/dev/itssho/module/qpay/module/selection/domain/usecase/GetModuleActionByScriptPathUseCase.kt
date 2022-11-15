package dev.itssho.module.qpay.module.selection.domain.usecase

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.qpay.module.selection.domain.entity.Script
import dev.itssho.module.qpay.module.selection.domain.repository.ScriptRepository
import reflection.castNotNull
import reflection.castOrThrow

class GetModuleActionByScriptPathUseCase(
	private val scriptRepository: ScriptRepository,
) {

	operator fun invoke(path: String): ModuleAction {
		val scripts = scriptRepository.get()

		return scripts
			.firstOrNull { it.path == path }
			.castNotNull { "No script found with path: '${path}'" }
			.castOrThrow<Script.Loaded> { "User clicked on script which is not loaded. Current state: '$it'. Script path: '${path}'" }
			.moduleAction
	}
}