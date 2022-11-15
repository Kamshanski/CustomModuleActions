@file:Suppress("RemoveExplicitTypeArguments")

package dev.itssho.module.qpay.module.actor.di.module

import dev.itssho.module.component.idea.action.DirectoryOrPackageCreator
import dev.itssho.module.component.idea.action.FileCreator
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.hierarchy.controller.Controller
import dev.itssho.module.qpay.module.actor.di.component.QpayCreateKoinDi
import dev.itssho.module.qpay.module.common.domain.hierarchy.ControllerImpl
import dev.itssho.module.qpay.module.create.data.datasource.directory.DirectoryDataSource
import dev.itssho.module.qpay.module.create.data.datasource.file.FileDataSource
import dev.itssho.module.qpay.module.create.data.repository.DirectoryRepositoryImpl
import dev.itssho.module.qpay.module.create.data.repository.FileRepositoryImpl
import dev.itssho.module.qpay.module.create.domain.repository.DirectoryRepository
import dev.itssho.module.qpay.module.create.domain.repository.FileRepository
import dev.itssho.module.qpay.module.create.domain.usecase.ImplementHierarchyUseCase
import dev.itssho.module.qpay.module.create.presentation.QpayCreateViewModel
import dev.itssho.module.qpay.module.create.ui.CreateUi
import dev.itssho.module.qpay.module.structure.actor.di.UiScopeQ
import dev.itssho.module.util.koin.factoryOf
import dev.itssho.module.util.koin.parametrizedLocalKoinScopeFactoryOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.swing.Swing
import org.koin.core.module.Module
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.ScopeDSL
import org.koin.dsl.bind
import org.koin.dsl.module

fun makeCreateDataModule(commonDataModule: Module) = module {
	single { DirectoryOrPackageCreator(get<JBContext>().ideProject) }
	single { FileCreator(get<JBContext>().ideProject) }

	singleOf(::DirectoryDataSource)
	singleOf(::FileDataSource)

	singleOf(::DirectoryRepositoryImpl) bind DirectoryRepository::class
	singleOf(::FileRepositoryImpl) bind FileRepository::class
}.apply {
	includes(commonDataModule)
}

private fun ScopeDSL.declareCreateFeatureDomainEntries() {
	factoryOf(::ImplementHierarchyUseCase)
	factoryOf(::ControllerImpl) bind Controller::class
}

fun makeCreateFeatureModule(commonFeatureModule: Module, createDataModule: Module): Module = module {
	parametrizedLocalKoinScopeFactoryOf(::QpayCreateKoinDi) {
		declareCreateFeatureDomainEntries()

		scoped(UiScopeQ) { CoroutineScope(Job() + Dispatchers.Swing) }
		scopedOf(::QpayCreateViewModel)
		scoped { CreateUi(get(), get(), get<CoroutineScope>(UiScopeQ)) }
	}
}.apply {
	includes(commonFeatureModule, createDataModule)
}