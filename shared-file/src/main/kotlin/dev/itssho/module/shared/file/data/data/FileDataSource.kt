package dev.itssho.module.shared.file.data.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Path

class FileDataSource {

	suspend fun read(path: String): String = withContext(Dispatchers.IO) {
		Files.readString(Path.of(path))
	}

	suspend fun write(path: String, content: String) = withContext(Dispatchers.IO) {
		Files.writeString(Path.of(path), content)
	}
}