package dev.itssho.module.qpay.module.name.domain.usecase

import contains
import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.name.Issue
import dev.itssho.module.hierarchy.storage.MutableValueStorage

class SubmitModuleNameUseCase(
	private val validateModuleNameUseCase: ValidateModuleNameUseCase,
	private val moduleAction: ModuleAction,
	private val valueStorage: MutableValueStorage,
) {

	operator fun invoke(name: String): LinkedHashSet<Issue> {
		val issues = validateModuleNameUseCase(name)

		return if (issues.contains { it is Issue.Error }) {
			issues
		} else {
			moduleAction.nameHandler.handleResult(name, valueStorage)
			LinkedHashSet(0)
		}
	}
}