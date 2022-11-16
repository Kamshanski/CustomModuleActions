package dev.itssho.module.qpay.module.actor.di

import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.qpay.module.common.di.makeCommonDataModule
import dev.itssho.module.qpay.module.common.di.makeCommonFeatureModule
import dev.itssho.module.qpay.module.create.di.makeCreateDataModule
import dev.itssho.module.qpay.module.create.di.makeCreateFeatureModule
import dev.itssho.module.qpay.module.name.deprecated.di.makeDeprecatedNameDataModule
import dev.itssho.module.qpay.module.name.deprecated.di.makeDeprecatedNameFeatureModule
import dev.itssho.module.qpay.module.name.di.makeNameDataModule
import dev.itssho.module.qpay.module.name.di.makeNameFeatureModule
import dev.itssho.module.qpay.module.selection.di.makeSelectionDataModule
import dev.itssho.module.qpay.module.selection.di.makeSelectionModule
import dev.itssho.module.qpay.module.structure.di.makeStructureDataModule
import dev.itssho.module.qpay.module.structure.di.makeStructureFeatureModule
import dev.itssho.module.shared.file.di.makeSharedFileDataModule
import dev.itssho.module.shared.file.di.makeSharedFileDomainModule
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication
import org.koin.dsl.module


/** Вкладывать common в другие модули не надо. Внутри makeDi это уже делается */
fun makeDi(jbContext: JBContext): KoinApplication {

	val koinApp = koinApplication()
	koinApp.allowOverride(false)

	val koinModule = module { single { koinApp.koin } }

	val sharedFileDataModule = makeSharedFileDataModule()
	val sharedFileFeatureModule = makeSharedFileDomainModule(sharedFileDataModule = sharedFileDataModule)

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
