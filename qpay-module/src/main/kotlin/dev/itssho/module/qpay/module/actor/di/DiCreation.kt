package dev.itssho.module.qpay.module.actor.di

import delegate.unsafeLazy
import dev.itssho.module.core.context.ProjectWindowClickContext
import dev.itssho.module.qpay.module.create.di.makeCreateModule
import dev.itssho.module.qpay.module.name.deprecated.di.makeQpayNameModule
import dev.itssho.module.qpay.module.name.di.makeNameModule
import dev.itssho.module.qpay.module.selection.di.makeSelectionModule
import dev.itssho.module.qpay.module.structure.di.makeStructureModule
import dev.itssho.module.service.action.module.di.makeModuleActionServiceModule
import dev.itssho.module.service.preferences.di.makePreferencesServiceModule
import dev.itssho.module.shared.file.di.makeSharedFileModule
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication

class ModuleHost(private val context: ProjectWindowClickContext, private val koin: Koin) {

	val rootModule by unsafeLazy { makeRootModule(context, koin) }

	val sharedFileModule by unsafeLazy { makeSharedFileModule() }

	val preferencesServiceModule by unsafeLazy { makePreferencesServiceModule() }
	val moduleActionServiceModule by unsafeLazy {
		makeModuleActionServiceModule(
			rootModule = rootModule,
			sharedFileModule = sharedFileModule,
			preferencesServiceModule = preferencesServiceModule,
		)
	}
}

class StepModuleHost(host: ModuleHost) {

	val selectionModule by unsafeLazy { makeSelectionModule(moduleActionServiceModule = host.moduleActionServiceModule) }

	val qpayNameModule by unsafeLazy { makeQpayNameModule(rootModule = host.rootModule) }

	val nameModule by unsafeLazy { makeNameModule(moduleActionServiceModule = host.moduleActionServiceModule) }

	val structureModule by unsafeLazy { makeStructureModule(rootModule = host.rootModule) }

	val createModule by unsafeLazy { makeCreateModule(rootModule = host.rootModule) }
}

fun makeDi(context: ProjectWindowClickContext): KoinApplication = koinApplication {
	allowOverride(false)

	val host = ModuleHost(context, koin)
	val stepHost = StepModuleHost(host)

	stepHost.run {
		modules(
			selectionModule,
			qpayNameModule,
			nameModule,
			structureModule,
			createModule,
		)
	}
}
