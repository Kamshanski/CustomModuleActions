package dev.itssho.module.shared.file.domain.repository

interface FileSystemRepository {

	suspend fun getFiles(folderPath: String, fileExtension: String?): List<String>
}
