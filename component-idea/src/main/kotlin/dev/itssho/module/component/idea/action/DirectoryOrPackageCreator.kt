package dev.itssho.module.component.idea.action

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Comparing
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileSystemItem
import com.intellij.util.IncorrectOperationException
import dev.itssho.module.component.idea.action.idea.MyCreateDirectoryOrPackageHandler
import dev.itssho.module.component.idea.common.IdeaException

// TODO Сейчас Ctrl+Z не работает, т.к. создание папок выполняетс в doCreateElement не задаётся groupId.
class DirectoryOrPackageCreator(private val project: Project) {

	private companion object {

		const val PACKAGE_OR_DIRECTORY_DELIMITERS = ".\\/"
		const val DELIMITER = "/"
	}

	private fun createHandler(rootDirectory: PsiDirectory): MyCreateDirectoryOrPackageHandler {
		return MyCreateDirectoryOrPackageHandler(
			project,
			rootDirectory,
			true,    // Флаг определяет, создаётся папка или пакет. Влияет только на сообщения об ошибке. Пусть всегда будет папка
			PACKAGE_OR_DIRECTORY_DELIMITERS,
		)
	}

	@Throws(RuntimeException::class)
	fun create(directory: List<String>, rootDirectory: PsiDirectory): PsiDirectory {
		val handler = createHandler(rootDirectory)

		val path = directory.joinToString(separator = DELIMITER)

		val canCreate = handler.checkInput(path)

		if (!canCreate) {
			val cause = handler.error
			throw IdeaException("Error for directory creation '${directory.joinToString(separator = "/")}' with cause: ${canCreate::class.simpleName}", cause)
		} else {
			handler.canClose(path)

			val createdDirectory = handler.createdDirectory ?: throw IdeaException("Directory '${directory.joinToString(separator = "/")}' is not created at $rootDirectory. Message dialog with error message is supposed to be shown.")

			return createdDirectory
		}
	}
}