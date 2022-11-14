package dev.itssho.module.shared.file.domain.repository

interface FileRepository {

	suspend fun read(path: String): String

	suspend fun write(path: String, content: String)
}