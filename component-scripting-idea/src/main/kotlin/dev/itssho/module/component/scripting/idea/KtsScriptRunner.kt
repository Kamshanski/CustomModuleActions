package dev.itssho.module.component.scripting.idea

import com.intellij.ide.script.IdeScriptEngine
import dev.itssho.module.component.scripting.GeneralScriptRunnerInfo
import dev.itssho.module.component.scripting.ScriptEvalException
import dev.itssho.module.component.scripting.ScriptRunner

class KtsScriptRunner(private val engine: IdeScriptEngine, override val generalInfo: GeneralScriptRunnerInfo) : ScriptRunner {

	@Suppress("UNCHECKED_CAST")
	@Throws(ScriptEvalException::class, ClassCastException::class)
	override fun <T : Any> load(script: String): T? {
		val result = runScript(script)

		return if (result == null) {
			null
		} else {
			result as T
		}
	}

	override fun execute(script: String) {
		runScript(script)
	}

	private fun runScript(script: String): Any? =
		try {
			engine.eval(script)
		} catch (ex: Throwable) {    // Может быть Error, которые тоже нужно отлавливать
			throw ScriptEvalException("Crash while evaluating script", ex)
		}
}
