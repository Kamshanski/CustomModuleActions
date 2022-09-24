package dev.itssho.module.qpay.module.create.data.datasource.directory

import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import dev.itssho.module.component.idea.action.DirectoryOrPackageCreator
import dev.itssho.module.core.actor.JBContext

class DirectoryDataSource(
	private val context: JBContext,
	private val creationHandler: DirectoryOrPackageCreator,
) {

	private companion object {

		const val PATH_DELIMITER = "/"
	}

	private val cache = HashMap<String, PsiDirectory>()

	init {
		save(context.rootDirectory)
	}

	fun get(directory: List<String>, rootDir: PsiDirectory): PsiDirectory? {
		if (directory.isEmpty()) {
			return null
		}

		val fromCache = getFromCache(directory, rootDir)
		fromCache?.let { return it }

		val fromProjectRoot = findSubdirectory(directory, context.rootDirectory)
		fromProjectRoot?.let { return it }

		return null
	}

	private fun findSubdirectory(directoryToSeek: List<String>, rootDir: PsiDirectory): PsiDirectory? {
		var survived: PsiDirectory? = null
		var nodeCandidates: Array<PsiElement> = rootDir.children
		for (node in directoryToSeek) {
			if (nodeCandidates.isEmpty()) {
				return null
			}

			survived = nodeCandidates.firstOrNull { it is PsiDirectory && it.name == node } as? PsiDirectory

			if (survived == null) {
				return null
			}

			nodeCandidates = survived.children
		}

		survived?.also {
			save(it)
		}

		return survived
	}

	fun getOrCreate(directory: List<String>): PsiDirectory =
		get(directory, context.rootDirectory) ?: createRecursively(directory)

	private fun createRecursively(fullDirectory: List<String>): PsiDirectory {
		var parent = context.rootDirectory
		var directory = emptyList<String>()

		for (node in fullDirectory) {
			directory = directory + node
			val dirPsi = get(directory, parent) ?: create(listOf(node), parent)
			parent = dirPsi
		}

		return parent
	}

	private fun create(directory: List<String>, parent: PsiDirectory): PsiDirectory {
		val newDir = creationHandler.create(directory, parent)
		save(newDir)
		return newDir
	}

	private fun getFromCache(directory: List<String>, rootDir: PsiDirectory): PsiDirectory? {
		val path = makePath(directory, rootDir)
		val savedDirectory = cache[path]
		if (savedDirectory != null) {
			if (savedDirectory.isValid && savedDirectory.isPhysical) {
				return savedDirectory
			} else {
				cache.remove(path)
			}
		}
		return null
	}

	private fun save(element: PsiDirectory) {
		cache[element.virtualFile.path] = element
	}



	private fun makePath(directory: List<String>, rootDir: PsiDirectory): String {
		val root = rootDir.virtualFile.path
		val searchPath = directory.joinToString(separator = PATH_DELIMITER)

		return root + PATH_DELIMITER + searchPath
	}
}