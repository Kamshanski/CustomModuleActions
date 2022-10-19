package dev.itssho.module.qpay.module.name.presentation.validation

import dev.itssho.module.hierarchy.extension.reportError
import dev.itssho.module.hierarchy.extension.reportWarning
import dev.itssho.module.hierarchy.name.IssueReporter
import dev.itssho.module.hierarchy.name.NameHandler

class NameHandlerImpl(private val externalNameHandler: NameHandler? = null) : NameHandler {

	private companion object {

		val ILLEGAL_CHARS = "<>:\"/\\|?*\n\t \b\r"
		val ACCEPTABLE_START = Regex("[A-Za-z]")
	}

	override fun validate(fullName: String, reporter: IssueReporter) {
		if (fullName.isBlank()) {
			reporter.reportError("Module name is Blank or Empty")
			return
		}

		if (fullName.any { !it.isAscii() }) {
			reporter.reportError("Has non ASCII chars")
		}

		val presentIllegalChars = ILLEGAL_CHARS.filter { fullName.contains(it) }
		if (presentIllegalChars.isNotEmpty()) {
			presentIllegalChars.toSet().forEach { char ->
				reporter.reportError("Has illegal char '$char'")
			}
		}

		if (!fullName.first().toString().matches(ACCEPTABLE_START)) {
			reporter.reportWarning("Starts with non-letter char")
		}

		externalNameHandler?.validate(fullName, reporter)
	}

	private inline fun Char.isAscii(): Boolean =
		this.toInt() < 128

	override fun getInitialName(): String {
		return externalNameHandler?.getInitialName() ?: ""
	}
}