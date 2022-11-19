package dev.itssho.module.service.action.module.data.datasource

import dev.itssho.module.service.action.module.ModuleActionServiceFactory
import dev.itssho.module.service.action.module.domain.entity.Script
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.nio.file.Path

class ModuleActionDataSource(
	private val moduleActionServiceFactory: ModuleActionServiceFactory,
) {

	fun clearAbsentItems(presentScriptsPaths: List<String>) {
		val paths = presentScriptsPaths.map { Path.of(it) }
		moduleActionService.clearAbsentItems(paths)
	}

	fun loadScripts(scriptsPaths: List<String>): Flow<List<Script>> {
		val paths = scriptsPaths.map { Path.of(it) }
		moduleActionService.loadActions(paths)
		return moduleActionService.state.map { map -> map.values.toList() }
	}

	fun getScripts(): List<Script> {
		val scriptsMap = moduleActionService.state.value
		return scriptsMap.values.toList()
	}

	private val moduleActionService
		get() = moduleActionServiceFactory.get()
}