package dev.itssho.module.component.scripting

data class GeneralScriptRunnerInfo(
	val engineName: String,
	val engineVersion: String,
	val languageName: String,
	val languageVersion: String,
	val fileExtensions: List<String>,
)
