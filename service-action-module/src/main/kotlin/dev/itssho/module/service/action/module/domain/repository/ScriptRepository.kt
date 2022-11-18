package dev.itssho.module.service.action.module.domain.repository

import dev.itssho.module.service.action.module.domain.entity.Script
import kotlinx.coroutines.flow.Flow

interface ScriptRepository {

	fun clearAbsentItems(presentScriptsPaths: List<String>)

	fun get(): List<Script>

	fun load(scriptsPaths: List<String>): Flow<List<Script>>
}