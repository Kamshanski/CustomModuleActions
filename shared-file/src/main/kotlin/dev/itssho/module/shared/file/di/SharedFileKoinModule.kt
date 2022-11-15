package dev.itssho.module.shared.file.di

import dev.itssho.module.shared.file.data.data.FileDataSource
import dev.itssho.module.shared.file.data.repository.FileRepositoryImpl
import dev.itssho.module.shared.file.domain.repository.FileRepository
import dev.itssho.module.shared.file.domain.usecase.GetFilesInFolderUseCase
import dev.itssho.module.shared.file.domain.usecase.ReadFileUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun makeSharedFileDataModule() = module {
	singleOf(::FileDataSource)
	singleOf(::FileRepositoryImpl) bind FileRepository::class
}

fun makeSharedFileFeatureModule(sharedFileDataModule: Module) = module {
	factoryOf(::ReadFileUseCase)
	factoryOf(::GetFilesInFolderUseCase)
}.apply {
	includes(sharedFileDataModule)
}