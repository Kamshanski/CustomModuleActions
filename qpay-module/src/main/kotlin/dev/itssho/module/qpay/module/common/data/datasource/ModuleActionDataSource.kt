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

// TODO доставать путь до скрипта из настроек
class ModuleActionDataSource(private val scriptRunnerFactory: IdeaKtsScriptRunnerFactory) {

	@Volatile
	private var cachedModuleAction: ModuleAction? = null

	val mutex = Mutex()

	fun isInitialized(): Boolean = cachedModuleAction != null

	suspend fun getModuleAction(): ModuleAction {
		var localInstance = cachedModuleAction
		if (localInstance == null) {
			mutex.withLock {
				localInstance = cachedModuleAction
				if (localInstance == null) {
					localInstance = compileScriptForModuleAction()
					cachedModuleAction = localInstance
				}
			}
		}
		return localInstance!!
	}

	private suspend fun compileScriptForModuleAction(): ModuleAction = withContext(Dispatchers.IO) {
		val filePath = "C:\\_Coding\\InteliJ Idea\\ModuleCreator\\qpay-module\\src\\test\\kotlin\\script\\QpayModule.kts".let { Path.of(it) }
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
