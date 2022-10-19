package dev.itssho.module.qpay.module.create.data.datasource.file

import com.intellij.lang.Language
import com.intellij.lang.java.JavaLanguage
import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.PsiDirectory
import dev.itssho.module.component.idea.action.FileCreator
import dev.itssho.module.core.actor.JBContext
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.plugins.groovy.GroovyLanguage
import java.util.Locale

class FileDataSource(
	private val fileCreator: FileCreator,
	private val context: JBContext,
) {

	/** fileName with extension */
	fun create(directory: PsiDirectory, fullFileName: String, content: CharSequence) {
		require(fullFileName.matches(FILE_NAME_MASK)) { "File name '$fullFileName' does not satisfy regex '${FILE_NAME_MASK.pattern}' " }

		val language = defineLanguage(fullFileName)

		fileCreator.create(name = fullFileName, language = language, content = content, directory = directory)
	}

	private fun defineLanguage(fileName: String): Language {
		val ext = fileName.substringAfterLast('.', missingDelimiterValue = "")
		val lang = when (ext) {
			"md" -> Language.findLanguageByID("Markdown")
			"kt", "kts" -> KotlinLanguage.INSTANCE
			"xml" -> XMLLanguage.INSTANCE
			"gradle" -> GroovyLanguage
			"java" -> JavaLanguage.INSTANCE
			else -> Language.findLanguageByID(ext) ?: Language.findLanguageByID(ext.toUpperCase(Locale.US))
		}

		return lang ?: Language.ANY
	}

	/** fileName with extension */
	fun getContent(directory: PsiDirectory, fullFileName: String): String {
		val file = directory.files.firstOrNull { file -> file.name == fullFileName }
			?: throw NoSuchElementException("No file '$fullFileName' was found in '${directory.virtualFile.path}'")

		return file.text
	}

	fun setContent(directory: PsiDirectory, fullFileName: String, newContent: String) {
		val file = directory.files.firstOrNull { file -> file.name == fullFileName }!!

		VfsUtil.saveText(file.virtualFile, newContent)
	}
}