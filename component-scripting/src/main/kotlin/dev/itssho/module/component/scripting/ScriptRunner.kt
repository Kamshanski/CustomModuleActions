package dev.itssho.module.component.scripting

interface ScriptRunner {

	val generalInfo: GeneralScriptRunnerInfo

	@Throws(ScriptEvalException::class, ClassCastException::class)
	fun <T : Any> load(script: String): T?

	@Throws(ScriptEvalException::class)
	fun execute(script: String)
}