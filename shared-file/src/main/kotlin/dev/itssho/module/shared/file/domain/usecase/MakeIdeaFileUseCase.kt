package dev.itssho.module.shared.file.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Path

// TODO Сделать норм логирование
class MakeIdeaFileUseCase {

	suspend operator fun invoke(fileName: String, content: String) {
		val clearFileName = fileName.replace(Regex("[\\\\/\\:\\?\\*\\<\\>\\|]"), "_")
		val path = Path.of("C:\\Users\\Dawan\\Desktop\\ErrorLogs\\$clearFileName")
		withContext(Dispatchers.IO) {
			Files.write(path, content.toByteArray())
		}
	    // TODO implement
	}
}