package dev.itssho.module.qpay.module.common.data.repository

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.qpay.module.common.data.datasource.ModuleActionDataSource
import dev.itssho.module.qpay.module.common.domain.repository.ModuleActionRepository
import kotlinx.coroutines.runBlocking

class ModuleActionRepositoryImpl(
	private val dataSource: ModuleActionDataSource
) : ModuleActionRepository {

	@Throws(IllegalStateException::class)
	override fun getCached(): ModuleAction {
		if (!dataSource.isInitialized()) {
			throw IllegalStateException("Module Action is not initialize yet")
		}

		return runBlocking { dataSource.getModuleAction() }
	}

	override suspend fun get(): ModuleAction {
		return dataSource.getModuleAction()
	}
}