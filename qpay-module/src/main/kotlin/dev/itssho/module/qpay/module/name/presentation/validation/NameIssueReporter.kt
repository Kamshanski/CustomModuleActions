package dev.itssho.module.qpay.module.name.presentation.validation

import dev.itssho.module.hierarchy.name.Issue
import dev.itssho.module.hierarchy.name.IssueReporter

class NameIssueReporter : IssueReporter {

	val issues = linkedSetOf<Issue>()

	override fun report(issue: Issue) {
		issues.add(issue)
	}
}