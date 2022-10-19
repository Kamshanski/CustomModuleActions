package dev.itssho.module.qpay.module.actor.di.module

import dev.itssho.module.core.actor.Context
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.core.actor.SwingContext
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.qpay.module.common.data.datasource.ModuleActionDataSource
import dev.itssho.module.qpay.module.common.data.repository.ModuleActionRepositoryImpl
import dev.itssho.module.qpay.module.common.domain.repository.ModuleActionRepository
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.common.domain.usecase.GetModuleActionUseCase
import dev.itssho.module.qpay.module.common.domain.usecase.GetModuleNameUseCase
import dev.itssho.module.qpay.module.common.domain.usecase.LoadModuleActionUseCase
import dev.itssho.module.qpay.module.common.domain.usecase.SetModuleNameUseCase
import dev.itssho.module.qpay.module.structure.actor.di.DataScopeQ
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module

fun makeCommonDataModule(jbContext: JBContext?, swingContext: SwingContext?): Module =
	module {
		when {
			jbContext != null    -> single { jbContext } bind Context::class
			swingContext != null -> single { swingContext } bind Context::class
			else                 -> throw IllegalArgumentException("Specify Context in di")
		}

		single(DataScopeQ) { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
		singleOf(::FullyEditableValueStorage) binds arrayOf(ValueStorage::class, MutableValueStorage::class)

		singleOf(::ModuleActionDataSource)

		singleOf(::ModuleActionRepositoryImpl) bind ModuleActionRepository::class

		factoryOf(::SetModuleNameUseCase)
		factoryOf(::GetModuleNameUseCase)
		factoryOf(::GetModuleActionUseCase)
		factoryOf(::LoadModuleActionUseCase)
	}

fun makeCommonModule() = module {}
