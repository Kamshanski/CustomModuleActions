package dev.itssho.module.service.action.module

import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import dev.itssho.module.service.action.module.internal.manager.ActionItemsManager
import kotlinx.coroutines.flow.StateFlow
import java.nio.file.Path

class ModuleActionService(@Suppress("unused") private val ideProject: Project) : Disposable {

	private val actionItemsManager: ActionItemsManager = ActionItemsManager()

	val state: StateFlow<Map<Path, ActionItem>> get() = actionItemsManager.state

	fun clearAbsentItems(presentScriptsPaths: List<Path>) {
		actionItemsManager.clearAbsentItems(presentScriptsPaths)
	}

	fun loadActions(scriptsPaths: List<Path>) {
		synchronized(this) {
			for (path in scriptsPaths) {
				actionItemsManager.update(path)
			}
		}
	}

	override fun dispose() {
		actionItemsManager.dispose()
	}
}
