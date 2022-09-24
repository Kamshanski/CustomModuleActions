package dev.itssho.module.qpay.module.create.domain.repository

// Нет смысла делить репозиторий файлов на несколько интерфейсов
interface FileRepository {

	/** fileName with extension */
	@Throws(RuntimeException::class)
	fun createFile(
		directoryChain: List<String>,
		fileName: String,
		content: CharSequence,
	)

	@Throws(RuntimeException::class)
	fun getContent(directoryToSeek: List<String>, fileName: String): String

	@Throws(RuntimeException::class)
	fun setContent(directoryToSeek: List<String>, fileName: String, content: String)
}