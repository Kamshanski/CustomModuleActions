package dev.itssho.module.qpay.module.common.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.dsl.module

fun makeCommonDataModule(rootModule: Module, sharedFileModule: Module): Module =
	module {
		single(DataScopeQ) { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
	}.apply {
		includes(rootModule)
		includes(sharedFileModule)
	}


fun makeCommonFeatureModule(commonDataModule: Module, sharedFileDomainModule: Module, preferencesServiceModule: Module) =
	module {
		includes(commonDataModule)
		includes(sharedFileDomainModule)
		includes(preferencesServiceModule)
	}
