package dev.itssho.module.shared.file.domain.usecase

import dev.itssho.module.shared.file.domain.repository.FileRepository

class GetFilesInFolderUseCase(private val repository: FileRepository) {

	suspend operator fun invoke(folderPath: String, fileExtension: String?): List<String> =
		repository.getFiles(folderPath, fileExtension)
}