package dev.itssho.module.qpay.module.selection.data.repository

import dev.itssho.module.qpay.module.selection.data.datasource.ModuleActionDataSource
import dev.itssho.module.qpay.module.selection.domain.entity.Script
import dev.itssho.module.qpay.module.selection.domain.repository.ScriptRepository
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