package dev.itssho.module.shared.file.di

import dev.itssho.module.shared.file.data.data.FileDataSource
import dev.itssho.module.shared.file.data.data.FileSystemDataSource
import dev.itssho.module.shared.file.data.repository.FileRepositoryImpl
import dev.itssho.module.shared.file.data.repository.FileSystemRepositoryImpl
import dev.itssho.module.shared.file.domain.repository.FileRepository
import dev.itssho.module.shared.file.domain.repository.FileSystemRepository
import dev.itssho.module.shared.file.domain.usecase.GetFilesInFolderUseCase
import dev.itssho.module.shared.file.domain.usecase.ReadFileUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun makeSharedFileModule() = module {
	singleOf(::FileDataSource)
	singleOf(::FileSystemDataSource)
	singleOf(::FileRepositoryImpl) bind FileRepository::class
	singleOf(::FileSystemRepositoryImpl) bind FileSystemRepository::class

	factoryOf(::ReadFileUseCase)
	factoryOf(::GetFilesInFolderUseCase)
}