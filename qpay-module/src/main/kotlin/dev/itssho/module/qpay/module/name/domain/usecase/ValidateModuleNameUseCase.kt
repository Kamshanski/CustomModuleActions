package dev.itssho.module.qpay.module.name.domain.usecase

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.name.DefaultIssueReporter
import dev.itssho.module.hierarchy.name.Issue

class ValidateModuleNameUseCase(
	private val moduleAction: ModuleAction,
) {

	operator fun invoke(name: String): LinkedHashSet<Issue> {
		val reporter = DefaultIssueReporter()
		moduleAction.nameHandler.validate(name, reporter)
		return reporter.issues
	}
}