package dev.itssho.module.qpay.module.create.data.datasource.file

import com.intellij.lang.Language
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.PsiDirectory
import dev.itssho.module.component.idea.action.FileCreator
import dev.itssho.module.core.context.ProjectWindowClickContext
import java.util.Locale

class FileDataSource(
	private val fileCreator: FileCreator,
	private val context: ProjectWindowClickContext,
) {

	/** fileName with extension */
	fun create(directory: PsiDirectory, fullFileName: String, content: CharSequence) {
		require(fullFileName.matches(FILE_NAME_MASK)) { "File name '$fullFileName' does not satisfy regex '${FILE_NAME_MASK.pattern}' " }

		val language = defineLanguage(fullFileName)

		fileCreator.create(name = fullFileName, language = language, content = content, directory = directory)
	}

	/* Языки ищутся по ID вместо прямого использования INSTANCE, потому что иногда KotlinLanguage.INSTANCE бросает ошибку
		java.lang.ClassCastException: class org.jetbrains.kotlin.idea.KotlinLanguage cannot be cast to class com.intellij.lang.Language
		(org.jetbrains.kotlin.idea.KotlinLanguage is in unnamed module of loader com.intellij.ide.plugins.cl.PluginClassLoader @3dd0432d; com.intellij.lang.Language is
		in unnamed module of loader com.intellij.util.lang.UrlClassLoader @5419f379)
			at dev.itssho.module.qpay.module.create.data.datasource.file.FileDataSource.defineLanguage(FileDataSource.kt:32)
			at dev.itssho.module.qpay.module.create.data.datasource.file.FileDataSource.create(FileDataSource.kt:23)
	*/
	private fun defineLanguage(fileName: String): Language {
		val ext = fileName.substringAfterLast('.', missingDelimiterValue = "")
		val lang = when (ext) {
			"md"        -> Language.findLanguageByID("Markdown")
			"kt", "kts" -> Language.findLanguageByID("kotlin") // org.jetbrains.kotlin.idea.KotlinLanguage.INSTANCE
			"xml"       -> Language.findLanguageByID("XML") // com.intellij.lang.xml.XMLLanguage.INSTANCE
			"gradle"    -> Language.findLanguageByID("Groovy") // org.jetbrains.plugins.groovy.GroovyLanguage
			"java"      -> Language.findLanguageByID("JAVA") // com.intellij.lang.java.JavaLanguage.INSTANCE
			else        -> Language.findLanguageByID(ext) ?: Language.findLanguageByID(ext.toUpperCase(Locale.US))
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