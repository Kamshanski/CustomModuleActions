package dev.itssho.module.shared.file.data.repository

import dev.itssho.module.shared.file.data.data.FileDataSource
import dev.itssho.module.shared.file.domain.repository.FileRepository

class FileRepositoryImpl(private val fileDataSource: FileDataSource) : FileRepository {

	override suspend fun read(path: String): String =
		fileDataSource.read(path)

	override suspend fun write(path: String, content: String) {
		fileDataSource.write(path, content)
	}
}