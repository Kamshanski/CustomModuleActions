package dev.itssho.module.qpay.module.create.domain.hierarchy

import dev.itssho.module.hierarchy.controller.Controller
import dev.itssho.module.qpay.module.create.domain.repository.DirectoryRepository
import dev.itssho.module.qpay.module.create.domain.repository.FileRepository

class ControllerImpl(
	private val directoryRepository: DirectoryRepository,
	private val fileRepository: FileRepository,
) : Controller {

	override fun createFile(directory: List<String>, fileName: String, content: String) {
		fileRepository.createFile(directory, fileName, content)
	}

	override fun createDirectory(directory: List<String>) {
		directoryRepository.create(directory)
	}

	@Deprecated("Not implemented")
	override fun editDirectoryName(rootDirectory: List<String>, newDirName: String) {
		TODO("Not yet implemented")
	}

	@Deprecated("Not implemented")
	override fun editFileName(directory: List<String>, oldFileName: String, newFileName: String) {
		TODO("Not yet implemented")
	}

	override fun editFileContent(directory: List<String>, fileName: String, edit: (String) -> String?) {
		val content = fileRepository.getContent(directory, fileName)
		val newContent = edit(content)
		if (newContent != null) {
			fileRepository.setContent(directory, fileName, newContent)
		}
	}

	@Deprecated("Not implemented")
	override fun deleteFile(directory: List<String>, fileName: String) {
		TODO("Not yet implemented")
	}

	@Deprecated("Not implemented")
	override fun deleteDirectory(directory: List<String>) {
		TODO("Not yet implemented")
	}
}