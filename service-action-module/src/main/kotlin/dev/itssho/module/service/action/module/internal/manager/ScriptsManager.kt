package dev.itssho.module.service.action.module.internal.manager

import chrono.SIMPLE_DATE_TIME_FORMATTER
import com.intellij.ide.script.IdeScriptEngineManager
import com.intellij.openapi.Disposable
import dev.itssho.module.component.scripting.ScriptRunner
import dev.itssho.module.component.scripting.idea.IdeaKtsScriptRunnerFactory
import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.importing.ReusableAction
import dev.itssho.module.service.action.module.domain.entity.Script
import dev.itssho.module.service.action.module.domain.entity.ScriptCompilation
import dev.itssho.module.service.action.module.internal.concurency.NewSafeThreadExecutor
import mapNotNullValues
import org.jetbrains.kotlin.tools.projectWizard.core.asPath
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.security.DigestInputStream
import java.security.MessageDigest
import java.time.LocalDateTime.now
import java.time.LocalTime

internal class ScriptsManager(maxThreadPoolSize: Int = 8) : Disposable {

	private val uniqueThreadExecutor = NewSafeThreadExecutor(maxThreadPoolSize)

	private val _state = ScriptsFlow()
	val state = _state.immutable()

	fun clearItems(presentScriptsPaths: List<Path>) {
		_state.modify { map ->
			map
				.filter { it.key in presentScriptsPaths }
				.recycleUsed()
		}
	}

	fun update(path: Path) {
		try {
			printlnLog("Start '${path.fileName}'")
			uniqueThreadExecutor.start()

			printlnLog("Recycle used scripts")
			_state.modify { map -> map.recycleUsed() }

			printlnLog("INIT start")
			val loadingItem = tryInitUpdate(path)
			if (loadingItem == null) {
				printlnLog("INITED FAIL. ALREADY LOADING: '${path.fileName}'")
				return
			}
			printlnLog("INITED '${path.fileName}'")

			val scriptFile = try {
				readScriptFile(path).also {
					printlnLog("FILE READ '${path.fileName}'")
				}
			} catch (ex: Exception) {
				printlnLog("FILE READ _ERROR_ '${path.fileName}'. '$ex'")
				setErrorItem(path, ex)
				return
			}
			val cache = loadingItem.cache
			if (cache != null && cache.hash.contentEquals(scriptFile.hash)) {
				printlnLog("RESTORE CACHED '${path.fileName}'")
				setCompiledItem(path, cache)
				return
			}

			asyncCompileScriptAndSetItem(scriptFile)

			uniqueThreadExecutor.clearInactiveThreads()
		} catch (ex: Throwable) {
			printlnLog("UNCAUGHT _ERROR_ '${path.fileName}'. '$ex'")
			setErrorItem(path, ex)
		}
	}

	private fun Map<Path, Script>.recycleUsed(): Map<Path, Script> =
		mapNotNullValues { recycle(it.value) }

	private fun recycle(script: Script): Script? {
		if (script is Script.Loaded && script.isUsed) {
			if (script.compilation.moduleAction is ReusableAction) {
				val reusable = script.compilation.moduleAction
				reusable.recycle()
				printlnLog("Recycled script ${script.path.asPath().fileName}")
				return clearLoadedScript(script)
			} else {
				printlnLog("Deleted used script ${script.path.asPath().fileName}")
				return null
			}
		}
		return script
	}

	private fun tryInitUpdate(path: Path): Script.Loading? {
		var loadingItem: Script.Loading? = null
		_state.modify { items ->
			val existingItem = items[path]
			val newItem = when (existingItem) {
				null,
				is Script.Failure -> Script.Loading(path.toString())
				is Script.Loaded  -> Script.Loading(path.toString(), cache = existingItem.compilation)
				is Script.Loading -> return null
			}

			loadingItem = newItem

			items.toMutableMap().apply {
				put(path, newItem)
			}
		}
		return loadingItem
	}

	private fun setErrorItem(path: Path, exception: Throwable) {
		_state.modify {
			it.toMutableMap().apply {
				val errorItem = Script.Failure(path.toString(), exception, now())
				put(path, errorItem)
			}
		}
	}

	private fun readScriptFile(scriptPath: Path): ScriptFile {
		val scriptFile = Files.newInputStream(scriptPath).use { stream ->
			val md = MessageDigest.getInstance("MD5")
			val scriptText = DigestInputStream(stream, md).use { dis ->
				val reader = InputStreamReader(dis, StandardCharsets.UTF_8.newDecoder())
				reader.readText()
			}
			val hash = md.digest()
			ScriptFile(path = scriptPath, content = scriptText, hash = hash)
		}

		return scriptFile
	}

	private fun setCompiledItem(path: Path, cache: ScriptCompilation) {
		_state.modify {
			it.toMutableMap().apply {
				val loadedItem = Script.Loaded(path.toString(), cache)
				put(path, loadedItem)
			}
		}
	}

	private fun asyncCompileScriptAndSetItem(scriptFile: ScriptFile) {
		uniqueThreadExecutor.addToQueue {
			if (shouldStop()) {
				printlnLog("_INTERRUPTION_ IN BEGINNING '${scriptFile.path.fileName}'")
				setErrorItem(scriptFile.path, InterruptedException("Compilation was interrupted"))
				return@addToQueue
			}

			try {
				val compiler = getCompiler()
				if (shouldStop()) {
					printlnLog("_INTERRUPTION_ COMPILER GET '${scriptFile.path.fileName}'")
					setErrorItem(scriptFile.path, InterruptedException("Compilation was interrupted"))
					return@addToQueue
				}

				val moduleAction = compiler.load<ModuleAction>(scriptFile.content)

				if (shouldStop()) {
					printlnLog("_INTERRUPTION_ LOAD '${scriptFile.path.fileName}'")
					setErrorItem(scriptFile.path, InterruptedException("Compilation was interrupted"))
					return@addToQueue
				}

				requireNotNull(moduleAction) { "Script returned null ModuleAction: '${scriptFile.path}'" }
				val compilation = ScriptCompilation(scriptFile.hash, now(), moduleAction)
				printlnLog("COMPILED '${scriptFile.path.fileName}'")
				setCompiledItem(scriptFile.path, compilation)
			} catch (ex: Throwable) {
				printlnLog("COMPILATION _ERROR_ '${scriptFile.path.fileName}'. '$ex'")
				setErrorItem(scriptFile.path, ex)
				return@addToQueue
			}
		}
	}

	private fun getCompiler(): ScriptRunner {
		val ideScriptManager = IdeScriptEngineManager.getInstance()
		val factory = IdeaKtsScriptRunnerFactory(ideScriptManager)
		val scriptRunner = factory.get()
		return scriptRunner
	}

	override fun dispose() {
		uniqueThreadExecutor.stop()
	}

	private fun printlnLog(msg: String) {
		println("DAWAN [${getLogTime()}]: $msg")
	}

	private fun getLogTime(): String =
		LocalTime.now().format(SIMPLE_DATE_TIME_FORMATTER)
}