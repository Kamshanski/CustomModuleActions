package dev.itssho.module.qpay.module.common.data.datasource

import dev.itssho.module.component.scripting.idea.IdeaKtsScriptRunnerFactory
import dev.itssho.module.hierarchy.importing.ModuleAction
import fullStackTraceString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Path

// TODO Заменить реализацию интерфейсом ScriptRunnerFactory
@Deprecated("Старая реализация. Заменится прокидыванием moduleAction через DI")
class OldModuleActionDataSource(private val scriptRunnerFactory: IdeaKtsScriptRunnerFactory) {

	private val mutex = Mutex()

	@Volatile
	private var cachedModuleAction: ModuleAction? = null

	fun getModuleAction(): ModuleAction {
		val action = cachedModuleAction ?: throw IllegalStateException("Module Action is not initialize yet")
		return action
	}

	suspend fun loadModuleAction(path: String): ModuleAction {
		var localInstance = cachedModuleAction
		if (localInstance == null) {
			mutex.withLock {
				localInstance = cachedModuleAction
				if (localInstance == null) {
					localInstance = compileScriptForModuleAction(path)
					cachedModuleAction = localInstance
				}
			}
		}
		return localInstance!!
	}

	private suspend fun compileScriptForModuleAction(path: String): ModuleAction = withContext(Dispatchers.IO) {
		val filePath = path.let { Path.of(it) }
		val script = Files.readString(filePath)

		val moduleAction = try {
			val ktsRunner = scriptRunnerFactory.get()
			ktsRunner.load<ModuleAction>(script) ?: throw IllegalArgumentException("Unable to get module action. Script return value is null. Check script carefully for any nullable return.")
		} catch (error: Throwable) {
			println(error.fullStackTraceString())
			throw error
		}

		return@withContext moduleAction
	}
}
