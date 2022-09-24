package dev.itssho.module.qpay.module.create.data.repository

import com.intellij.psi.PsiDirectory
import dev.itssho.module.qpay.module.create.data.datasource.directory.DirectoryDataSource
import dev.itssho.module.qpay.module.create.data.datasource.file.FileDataSource
import dev.itssho.module.qpay.module.create.domain.repository.FileRepository

class FileRepositoryImpl(
	private val fileDataSource: FileDataSource,
	private val directoryDataSource: DirectoryDataSource,
) : FileRepository {

	/** fileName with extension */
	override fun createFile(directoryChain: List<String>, fileName: String, content: CharSequence) {
		val directory = getDirectory(directoryChain)

		fileDataSource.create(directory, fileName, content)
	}

	override fun getContent(directoryToSeek: List<String>, fileName: String): String {
		val directory = getDirectory(directoryToSeek)
		val content = fileDataSource.getContent(directory, fileName)
		return content
	}

	override fun setContent(directoryToSeek: List<String>, fileName: String, content: String) {
		val directory = getDirectory(directoryToSeek)

		fileDataSource.setContent(directory, fileName, content)
	}

	private fun getDirectory(directory: List<String>): PsiDirectory =
		directoryDataSource.getOrCreate(directory)
}