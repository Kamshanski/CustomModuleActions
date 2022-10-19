package dev.itssho.module.qpay.module.common.data.datasource

import de.swirtz.ktsrunner.objectloader.KtsObjectLoader
import dev.itssho.module.hierarchy.importing.ModuleAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Path

// TODO доставать путь до скрипта из настроек
class ModuleActionDataSource {

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
					localInstance = compileScriptForModuleAction_temp()
					cachedModuleAction = localInstance
				}
			}
		}
		return localInstance!!
	}

	private suspend fun compileScriptForModuleAction_temp(): ModuleAction = withContext(Dispatchers.IO) {
		ModuleAction(
			name = "MyGreatName",
			hierarchyInitializer = HierarchyInitializerImpl(),
			valuesInitializer = ValuesInitializerImpl(),
			hierarchyProcessor = QpayHierarchyProcessor(),
		)
	}

	private suspend fun compileScriptForModuleAction(): ModuleAction = withContext(Dispatchers.IO) {
		val filePath = "C:\\_Coding\\InteliJ Idea\\ModuleCreator\\qpay-module\\src\\test\\kotlin\\script\\QpayModule.kts".let { Path.of(it) }
		val scriptStream = Files.newInputStream(filePath)

		val objLoader = KtsObjectLoader()
		val moduleAction = objLoader.load<ModuleAction>(scriptStream)

		return@withContext moduleAction
	}
}

