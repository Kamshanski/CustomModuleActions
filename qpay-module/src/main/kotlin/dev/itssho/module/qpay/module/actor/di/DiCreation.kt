package dev.itssho.module.qpay.module.actor.di

import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.core.actor.SwingContext
import dev.itssho.module.qpay.module.actor.di.module.makeCommonDataModule
import dev.itssho.module.qpay.module.actor.di.module.makeCommonModule
import dev.itssho.module.qpay.module.actor.di.module.makeCreateDataModule
import dev.itssho.module.qpay.module.actor.di.module.makeCreateModule
import dev.itssho.module.qpay.module.actor.di.module.other.makeSharedFileModule
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication
import org.koin.dsl.module


/** Вкладывать common в другие модули не надо. Внутри makeDi это уже делается */
fun makeDi(jbContext: JBContext? = null, swingContext: SwingContext? = null): KoinApplication {

	val koinApp = koinApplication()


	val koinModule = module { single { koinApp.koin } }


	val commonDataModule = makeCommonDataModule(jbContext, swingContext).apply {
		includes(
			koinModule,
			makeSharedFileModule()
		)
	}
	val commonModule = makeCommonModule().apply { includes(commonDataModule) }


	val nameDataModule = makeNameDataModule().apply { includes(commonDataModule) }
	val nameModule = makeNameModule().apply {
		includes(nameDataModule)
		includes(commonModule)
	}


	val structureDataModule = makeStructureDataModule().apply { includes(commonDataModule) }
	val structureModule = makeStructureModule().apply {
		includes(structureDataModule)
		includes(commonModule)

	}


	val createDataModule = makeCreateDataModule().apply { includes(commonDataModule) }
	val createModule = makeCreateModule().apply {
		includes(createDataModule)
		includes(commonModule)
	}


	koinApp.modules(
		commonDataModule, commonModule,
		nameDataModule, nameModule,
		structureDataModule, structureModule,
		createDataModule, createModule,
	)

	return koinApp
}
