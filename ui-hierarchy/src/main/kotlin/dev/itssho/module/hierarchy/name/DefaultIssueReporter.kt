package dev.itssho.module.hierarchy.name

class DefaultIssueReporter : IssueReporter {

	val issues = linkedSetOf<Issue>()

	override fun report(issue: Issue) {
		issues.add(issue)
	}
}