package dev.itssho.module.qpay.module.actor.di

import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.qpay.module.actor.di.module.makeCommonDataModule
import dev.itssho.module.qpay.module.actor.di.module.makeCommonFeatureModule
import dev.itssho.module.qpay.module.actor.di.module.makeCreateDataModule
import dev.itssho.module.qpay.module.actor.di.module.makeCreateFeatureModule
import dev.itssho.module.qpay.module.actor.di.module.makeDeprecatedNameDataModule
import dev.itssho.module.qpay.module.actor.di.module.makeDeprecatedNameFeatureModule
import dev.itssho.module.qpay.module.actor.di.module.makeNameDataModule
import dev.itssho.module.qpay.module.actor.di.module.makeNameFeatureModule
import dev.itssho.module.qpay.module.actor.di.module.makeSelectionDataModule
import dev.itssho.module.qpay.module.actor.di.module.makeSelectionModule
import dev.itssho.module.qpay.module.actor.di.module.makeStructureDataModule
import dev.itssho.module.qpay.module.actor.di.module.makeStructureFeatureModule
import dev.itssho.module.shared.file.di.makeSharedFileDataModule
import dev.itssho.module.shared.file.di.makeSharedFileFeatureModule
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication
import org.koin.dsl.module


/** Вкладывать common в другие модули не надо. Внутри makeDi это уже делается */
fun makeDi(jbContext: JBContext): KoinApplication {

	val koinApp = koinApplication()
	koinApp.allowOverride(false)

	val koinModule = module { single { koinApp.koin } }

	val sharedFileDataModule = makeSharedFileDataModule()
	val sharedFileFeatureModule = makeSharedFileFeatureModule(sharedFileDataModule = sharedFileDataModule)

	// TODO Common неудачное название. Правильный common должен включать JBContext, KoinModule и другие общие для абсолютно всех модулей сущности
	//  А общие для степов сущности должны быть отдельно
	val commonDataModule = makeCommonDataModule(
		jbContext = jbContext,
		koinModule = koinModule,
		sharedFileModule = sharedFileDataModule,
	)
	val commonFeatureModule = makeCommonFeatureModule(
		commonDataModule = commonDataModule,
		sharedFileFeatureModule = sharedFileFeatureModule,
	)

	val selectionDataModule = makeSelectionDataModule(commonDataModule = commonDataModule)
	val selectionFeatureModule = makeSelectionModule(
		commonFeatureModule = commonFeatureModule,
		selectionDataModule = selectionDataModule,
	)

	val deprecatedNameDataModule = makeDeprecatedNameDataModule(commonDataModule = commonDataModule)
	val deprecatedNameFeatureModule = makeDeprecatedNameFeatureModule(
		commonFeatureModule = commonFeatureModule,
		deprecatedNameDataModule = deprecatedNameDataModule,
	)

	val nameDataModule = makeNameDataModule(commonDataModule = commonDataModule)
	val nameFeatureModule = makeNameFeatureModule(
		commonFeatureModule = commonFeatureModule,
		nameDataModule = nameDataModule,
	)

	val structureDataModule = makeStructureDataModule(commonDataModule = commonDataModule)
	val structureFeatureModule = makeStructureFeatureModule(
		commonFeatureModule = commonFeatureModule,
		structureDataModule = structureDataModule,
	)

	val createDataModule = makeCreateDataModule(commonDataModule = commonDataModule)
	val createFeatureModule = makeCreateFeatureModule(
		commonFeatureModule = commonFeatureModule,
		createDataModule = createDataModule,
	)


	koinApp.modules(
		commonFeatureModule,
		selectionFeatureModule,
		deprecatedNameFeatureModule,
		nameFeatureModule,
		structureFeatureModule,
		createFeatureModule,
	)

	return koinApp
}
