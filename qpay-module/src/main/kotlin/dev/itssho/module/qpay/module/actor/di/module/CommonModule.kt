package dev.itssho.module.qpay.module.actor.di.module

import dev.itssho.module.core.actor.Context
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.core.actor.SwingContext
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.common.domain.usecase.SetModuleNameUseCase
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
		singleOf(::FullyEditableValueStorage) binds arrayOf(ValueStorage::class, MutableValueStorage::class)
		factoryOf(::SetModuleNameUseCase)
	}

fun makeCommonModule() = module {}
