package dev.itssho.module.qpay.module.selection.domain.repository

import dev.itssho.module.qpay.module.selection.domain.entity.Script
import kotlinx.coroutines.flow.Flow

interface ScriptRepository {

	fun clearAbsentItems(presentScriptsPaths: List<String>)

	fun get(): List<Script>

	fun load(scriptsPaths: List<String>): Flow<List<Script>>
}