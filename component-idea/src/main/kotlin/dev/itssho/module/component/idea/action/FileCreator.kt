package dev.itssho.module.component.idea.action

import com.intellij.ide.IdeBundle
import com.intellij.lang.Language
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import dev.itssho.module.component.idea.action.idea.AlreadyExistsException
import dev.itssho.module.component.idea.action.idea.invokeAndWaitForWriteCommand
import dev.itssho.module.component.idea.common.IdeaException

/**
 * https://plugins.jetbrains.com/docs/intellij/psi-files.html#how-do-i-create-a-psi-file
 */
class FileCreator(
	private val project: Project,
) {

	private val fileFactory = PsiFileFactory.getInstance(project)

	@Throws(RuntimeException::class)
	fun create(
		name: String,
		language: Language,
		content: CharSequence,
		directory: PsiDirectory,
		eventSystemEnabled: Boolean = true,
		markAsCopy: Boolean = true,
		noSizeLimit: Boolean = true,
	): PsiFile {
		val file = fileFactory.createFileFromText(
			name,
			language,
			content,
			eventSystemEnabled,
			markAsCopy,
			noSizeLimit,
		)
		// TODO Подобавлять входных параметров в сообщения ошибок по всему проекту.
		requireNotNull(file) { throw IdeaException("File creation failed. 'createFileFromText' returned null.") }

		val commandKey = IdeBundle.message("command.create.file")
		invokeAndWaitForWriteCommand(project, commandKey) {
			// TODO Чтобы добавить UNDO, нужно скопипастить схему с LocalHistory из создания файла
			directory.add(file)
		}

		return file
	}
}