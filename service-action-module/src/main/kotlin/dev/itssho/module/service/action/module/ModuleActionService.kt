package dev.itssho.module.service.action.module

import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import dev.itssho.module.service.action.module.domain.entity.Script
import dev.itssho.module.service.action.module.internal.manager.ScriptsManager
import kotlinx.coroutines.flow.StateFlow
import java.nio.file.Path

class ModuleActionService(@Suppress("unused") private val ideProject: Project) : Disposable {

	private val scriptsManager: ScriptsManager = ScriptsManager()

	val state: StateFlow<Map<Path, Script>> get() = scriptsManager.state

	fun clearAbsentItems(presentScriptsPaths: List<Path>) {
		scriptsManager.clearItems(presentScriptsPaths)
	}

	fun loadActions(scriptsPaths: List<Path>) {
		synchronized(this) {
			for (path in scriptsPaths) {
				scriptsManager.update(path)
			}
		}
	}

	override fun dispose() {
		scriptsManager.dispose()
	}
}
