package dev.itssho.module.qpay.module.selection.domain.usecase

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.service.action.module.domain.entity.Script
import dev.itssho.module.service.action.module.domain.usecase.GetScriptsUseCase
import reflection.castNotNull
import reflection.castOrThrow

class GetModuleActionByScriptPathUseCase(
	private val getScriptsUseCase: GetScriptsUseCase,
) {

	operator fun invoke(path: String): ModuleAction {
		val scripts = getScriptsUseCase()

		return scripts
			.firstOrNull { it.path == path }
			.castNotNull { "No script found with path: '${path}'" }
			.castOrThrow<Script.Loaded> { "User clicked on script which is not loaded. Current state: '$it'. Script path: '${path}'" }
			.useModuleAction()
	}
}