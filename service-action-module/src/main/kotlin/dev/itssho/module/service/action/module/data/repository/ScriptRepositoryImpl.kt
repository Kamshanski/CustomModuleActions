package dev.itssho.module.service.action.module.data.repository

import dev.itssho.module.service.action.module.data.datasource.ModuleActionDataSource
import dev.itssho.module.service.action.module.domain.entity.Script
import dev.itssho.module.service.action.module.domain.repository.ScriptRepository
import kotlinx.coroutines.flow.Flow

class ScriptRepositoryImpl(private val moduleActionDataSource: ModuleActionDataSource) : ScriptRepository {

	override fun clearAbsentItems(presentScriptsPaths: List<String>) {
		moduleActionDataSource.clearAbsentItems(presentScriptsPaths)
	}

	override fun get(): List<Script> =
		moduleActionDataSource.getScripts()

	override fun load(scriptsPaths: List<String>): Flow<List<Script>> =
		moduleActionDataSource.loadScripts(scriptsPaths)
}