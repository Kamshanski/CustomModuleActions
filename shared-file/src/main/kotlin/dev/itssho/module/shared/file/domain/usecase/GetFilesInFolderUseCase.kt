package dev.itssho.module.shared.file.domain.usecase

import dev.itssho.module.shared.file.domain.repository.FileSystemRepository

class GetFilesInFolderUseCase(private val fileSystemRepository: FileSystemRepository) {

	suspend operator fun invoke(folderPath: String, fileExtension: String?): List<String> =
		fileSystemRepository.getFiles(folderPath, fileExtension)
}