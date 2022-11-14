package dev.itssho.module.shared.file.data.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.asSequence

class FileDataSource {

	suspend fun read(path: String): String = withContext(Dispatchers.IO) {
		Files.readString(Path.of(path))
	}

	suspend fun write(path: String, content: String) = withContext(Dispatchers.IO) {
		Files.writeString(Path.of(path), content)
	}

	suspend fun getFiles(folderPath: String, fileExtension: String?): List<String> = withContext(Dispatchers.IO) {
		val path = Path.of(folderPath)
		val files = Files
			.walk(path)
			.asSequence()
			.filterNotNull()
			.let {
				if (fileExtension != null) {
					it.filter { path -> path.toString().endsWith(fileExtension) }
				} else {
					it
				}
			}
			.map { it.toString() }
			.toList()

		return@withContext files
	}
}