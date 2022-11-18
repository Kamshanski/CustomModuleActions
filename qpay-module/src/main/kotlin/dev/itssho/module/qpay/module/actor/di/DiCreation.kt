package dev.itssho.module.qpay.module.actor.di

import dev.itssho.module.core.context.ProjectWindowClickContext
import dev.itssho.module.qpay.module.common.di.makeCommonDataModule
import dev.itssho.module.qpay.module.common.di.makeCommonFeatureModule
import dev.itssho.module.qpay.module.create.di.makeCreateDataModule
import dev.itssho.module.qpay.module.create.di.makeCreateFeatureModule
import dev.itssho.module.qpay.module.name.deprecated.di.makeDeprecatedNameDataModule
import dev.itssho.module.qpay.module.name.deprecated.di.makeDeprecatedNameFeatureModule
import dev.itssho.module.qpay.module.name.di.makeNameDataModule
import dev.itssho.module.qpay.module.name.di.makeNameFeatureModule
import dev.itssho.module.qpay.module.selection.di.makeSelectionModule
import dev.itssho.module.qpay.module.structure.di.makeStructureDataModule
import dev.itssho.module.qpay.module.structure.di.makeStructureFeatureModule
import dev.itssho.module.service.action.module.di.makeModuleActionServiceModule
import dev.itssho.module.service.preferences.di.makePreferencesServiceModule
import dev.itssho.module.shared.file.di.makeSharedFileModule
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication

/** Вкладывать common в другие модули не надо. Внутри makeDi это уже делается */
fun makeDi(jbContext: ProjectWindowClickContext): KoinApplication {

	val koinApp = koinApplication()
	koinApp.allowOverride(false)

	val rootModule = makeRootModule(jbContext, koinApp.koin)

	val sharedFileModule = makeSharedFileModule()

	val preferencesServiceModule = makePreferencesServiceModule()
	val moduleActionServiceModule = makeModuleActionServiceModule(
		rootModule = rootModule,
		sharedFileModule = sharedFileModule,
		preferencesServiceModule = preferencesServiceModule,
	)

	// TODO Common неудачное название. Правильный common должен включать JBContext, KoinModule и другие общие для абсолютно всех модулей сущности
	//  А общие для степов сущности должны быть отдельно
	val commonDataModule = makeCommonDataModule(
		rootModule = rootModule,
		sharedFileModule = sharedFileModule,
	)
	val commonFeatureModule = makeCommonFeatureModule(
		commonDataModule = commonDataModule,
		sharedFileDomainModule = sharedFileModule,
		preferencesServiceModule = preferencesServiceModule,
	)

	val selectionFeatureModule = makeSelectionModule(
		commonFeatureModule = commonFeatureModule,
		moduleActionServiceModule = moduleActionServiceModule,
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
