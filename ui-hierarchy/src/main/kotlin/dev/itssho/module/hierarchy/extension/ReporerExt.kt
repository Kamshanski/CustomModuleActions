package dev.itssho.module.hierarchy.extension

import dev.itssho.module.hierarchy.name.Issue
import dev.itssho.module.hierarchy.name.IssueReporter

fun IssueReporter.reportWarning(message: String) {
	report(Issue.Warning(message))
}

fun IssueReporter.reportError(message: String) {
	report(Issue.Error(message))
}