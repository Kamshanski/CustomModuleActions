package dev.itssho.module.qpay.module.common.domain.repository

import dev.itssho.module.hierarchy.importing.ModuleAction

interface ModuleActionRepository {

	@Throws(IllegalStateException::class)
	fun getCached(): ModuleAction

	suspend fun get(): ModuleAction
}