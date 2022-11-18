package dev.itssho.module.listener.preload.script.di

import dev.itssho.module.core.context.JBContext
import dev.itssho.module.service.action.module.di.makeModuleActionServiceModule
import dev.itssho.module.service.preferences.di.makePreferencesServiceModule
import dev.itssho.module.shared.file.di.makeSharedFileModule
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import kotlin.reflect.KClass

internal class PreloadScriptDi(val context: JBContext) {

	private companion object {

		fun makeModuleActionServiceKoin(context: JBContext): KoinApplication = koinApplication {
			allowOverride(false)

			val rootModule = makeRootModule(context)

			val sharedFileModule = makeSharedFileModule()
			val preferencesServiceModule = makePreferencesServiceModule()

			val moduleActionServiceModule = makeModuleActionServiceModule(
				rootModule = rootModule,
				sharedFileModule = sharedFileModule,
				preferencesServiceModule = preferencesServiceModule,
			)

			modules(moduleActionServiceModule)
		}

		fun makeRootModule(context: JBContext) = module {
			single { context }
		}
	}

	private val koinApp = makeModuleActionServiceKoin(context)
	val koin = koinApp.koin

	fun <T> get(type: KClass<*>): T = koin.get(type)

	inline fun <reified T> get(): T = get(T::class)
}