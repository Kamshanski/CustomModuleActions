package dev.itssho.module.listener.preload.script.di

import org.koin.core.module.Module
import org.koin.dsl.module

internal fun makePreloadScriptDi(moduleActionServiceModule: Module) = module {

}.apply {
	includes(moduleActionServiceModule)
}