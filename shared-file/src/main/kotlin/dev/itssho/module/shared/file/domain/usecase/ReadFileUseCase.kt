package dev.itssho.module.shared.file.domain.usecase

import dev.itssho.module.shared.file.domain.repository.FileRepository

class ReadFileUseCase(private val fileRepository: FileRepository) {

	suspend operator fun invoke(path: String) {
		fileRepository.read(path)
	}
}