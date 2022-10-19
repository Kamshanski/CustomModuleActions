package dev.itssho.module.qpay.module.common.data.repository

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.qpay.module.common.data.datasource.ModuleActionDataSource
import dev.itssho.module.qpay.module.common.domain.repository.ModuleActionRepository

class ModuleActionRepositoryImpl(
	private val dataSource: ModuleActionDataSource
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