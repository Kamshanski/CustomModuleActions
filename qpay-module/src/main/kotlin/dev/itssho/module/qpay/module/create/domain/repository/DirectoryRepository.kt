package dev.itssho.module.qpay.module.create.domain.repository

import com.intellij.psi.PsiDirectory

interface DirectoryRepository {

	@Throws(RuntimeException::class)
	fun create(directory: List<String>): PsiDirectory
}