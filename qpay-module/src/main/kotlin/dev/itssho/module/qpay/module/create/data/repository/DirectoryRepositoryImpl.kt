package dev.itssho.module.qpay.module.create.data.repository

import dev.itssho.module.qpay.module.create.data.datasource.directory.DirectoryDataSource
import dev.itssho.module.qpay.module.create.domain.repository.DirectoryRepository

class DirectoryRepositoryImpl(
	private val directoryDataSource: DirectoryDataSource,
) : DirectoryRepository {

    override fun create(directory: List<String>) =
        directoryDataSource.getOrCreate(directory)
}