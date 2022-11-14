package dev.itssho.module.qpay.module.common.data.repository

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.qpay.module.common.data.datasource.OldModuleActionDataSource
import dev.itssho.module.qpay.module.common.domain.repository.ModuleActionRepository

@Deprecated("Старая реализация")
class ModuleActionRepositoryImpl(
	private val dataSource: OldModuleActionDataSource
) : ModuleActionRepository {

	@Throws(IllegalStateException::class)
	override fun getCached(): ModuleAction {
		return dataSource.getModuleAction()
	}

	override suspend fun get(path: String): ModuleAction {
		dataSource.loadModuleAction(path)
		return dataSource.getModuleAction()
	}
}