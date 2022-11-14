package dev.itssho.module.shared.file.data.repository

import dev.itssho.module.shared.file.data.data.FileSystemDataSource
import dev.itssho.module.shared.file.domain.repository.FileSystemRepository

class FileSystemRepositoryImpl(private val dataSource: FileSystemDataSource) : FileSystemRepository {

	override suspend fun getFiles(folderPath: String, fileExtension: String?): List<String> =
		dataSource.getFiles(folderPath, fileExtension)
}