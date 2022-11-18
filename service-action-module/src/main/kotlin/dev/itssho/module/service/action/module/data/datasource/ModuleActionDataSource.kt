package dev.itssho.module.service.action.module.data.datasource

import dev.itssho.module.service.action.module.ActionItem
import dev.itssho.module.service.action.module.ModuleActionService
import dev.itssho.module.service.action.module.domain.entity.Script
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.nio.file.Path

class ModuleActionDataSource(
	private val moduleActionService: ModuleActionService,
) {

	fun clearAbsentItems(presentScriptsPaths: List<String>) {
		val paths = presentScriptsPaths.map { Path.of(it) }
		moduleActionService.clearAbsentItems(paths)
	}

	fun loadScripts(scriptsPaths: List<String>): Flow<List<Script>> {
		val paths = scriptsPaths.map { Path.of(it) }
		moduleActionService.loadActions(paths)
		return moduleActionService.state.map { map -> map.values.map { convertActionItemToModule(it) } }
	}

	fun getScripts(): List<Script> {
		val scriptsMap = moduleActionService.state.value
		return scriptsMap.values.map { convertActionItemToModule(it) }
	}
}

private fun convertActionItemToModule(item: ActionItem): Script =
	item.run {
		when (this) {
			is ActionItem.Failure -> Script.Failure(path.toString(), exception, timestamp)
			is ActionItem.Loaded  -> Script.Loaded(path.toString(), compilation.moduleAction, compilation.timestamp)
			is ActionItem.Loading -> Script.Loading(path.toString())
		}
	}