package dev.itssho.module.component.scripting.idea

import com.intellij.ide.script.IdeScriptEngineManager
import contains
import dev.itssho.module.component.scripting.ScriptRunner
import dev.itssho.module.component.scripting.ScriptRunnerFactory
import string.lowercase

// TODO Изучить возможность отказа от IdeScriptEngineManager. Доставать скрипты можно из руками
//  Смотри [com.intellij.ide.script.IdeScriptEngineManagerImpl.myStateFuture]
// Как запускать скрипты на Intellij Idea https://intellij-support.jetbrains.com/hc/en-us/community/posts/4408788074386-How-to-use-the-Kts-ScriptEngine-in-an-IntelliJ-IDEA-plugin-
class IdeaKtsScriptRunnerFactory(private val scriptEngineManager: IdeScriptEngineManager) : ScriptRunnerFactory {

	@Throws(NoSuchElementException::class)
	override fun get(): ScriptRunner {
		val engineInfo = getKtsEngineInfo()
		val generalInfo = convertToGeneralInfo(engineInfo)
		val engine = scriptEngineManager.getEngine(engineInfo, null)
			?: throw IllegalStateException("Engine info was found, but script engine is absent. $engineInfo. $generalInfo")
		return KtsScriptRunner(engine, generalInfo)
	}

	private fun getKtsEngineInfo(): IdeScriptEngineManager.EngineInfo {
		val info = scriptEngineManager.engineInfos

		return info.firstOrNull {
			it.engineName.lowercase().contains("kotlin")
				|| it.languageName.lowercase().contains("kotlin")
				|| it.fileExtensions.contains { ext -> ext.lowercase() == "kts" }
		}
			?: throw NoSuchElementException("No Idea Script Engine found by name 'kotlin' or extension 'kts'")
	}
}