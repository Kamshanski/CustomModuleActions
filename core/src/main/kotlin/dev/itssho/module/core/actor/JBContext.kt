package dev.itssho.module.core.actor

import com.intellij.ide.IdeView
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import dev.itssho.module.component.chain.Separator
import dev.itssho.module.component.chain.any.AnyChain
import dev.itssho.module.component.chain.castTo
import dev.itssho.module.component.chain.splitToChain
import dev.itssho.module.core.ui.UiPlatform
import java.nio.file.Path

@Suppress("CanBeParameter", "MemberVisibilityCanBePrivate", "UsePropertyAccessSyntax")
class JBContext(
	val ideDataContext: DataContext,
	val ideView: IdeView,
	val ideProject: Project,
) : Context {

	override val platform: UiPlatform = UiPlatform.JET_BRAINS

	override val global: MutableMap<Any, Any> = mutableMapOf()

	/** [PsiDirectory] файла, выбранного сейчас в дереве проекта */
	val selectedDirectory: PsiDirectory = ideView.getOrChooseDirectory().takeNotNullOrExit()

	/** Имя проекта */
	val projectName: String = ideProject.name

	/** Путь от корня файловой системы ('C:' or '~') до папки Проекта (ИСКЛЮЧИТЕЛЬНО).
	 * @sample 'C:\aa\bbb\ccc' */
	val projectDirChain: AnyChain = makeProjectRootPath(selectedDirectory, projectName)

	/** Путь от корня файловой системы ('C:' or '~') до папки Проекта (ВКЛЮЧИТЕЛЬНО).
	 * @sample 'C:\aa\bbb\ccc\ProjectName' */
	val rootDirChain: AnyChain = projectDirChain + projectName

	/** PsiDirectory пути от корня файловой системы ('C:' or '~') до папки Проекта (ВКЛЮЧИТЕЛЬНО).
	 * TODO проверить, от C: он идёт или от начала проекта. Актуализировать описание
	 * @sample 'C:\aa\bbb\ccc\ProjectName' */
	val rootDirectory: PsiDirectory = findPsiDirectorySubDirectory(selectedDirectory, rootDirChain)

	companion object {

		fun make(ideContext: DataContext): JBContext = JBContext(
			ideDataContext = ideContext,
			ideView = LangDataKeys.IDE_VIEW.getData(ideContext).takeNotNullOrExit(),
			ideProject = CommonDataKeys.PROJECT.getData(ideContext).takeNotNullOrExit(),
		)

		private inline fun <reified T> T?.takeNotNullOrExit(): T {
			if (this == null) {
				val msg = "Error making ${JBContext::class.java.simpleName}: desired '${T::class.java.simpleName}' is null"
				throw ContextCreationException(msg)
			}

			return this
		}

		private fun makeProjectRootPath(selectedDirectory: PsiDirectory, projectName: String): AnyChain = selectedDirectory
			.virtualFile.path        									// 'C:\aa\bbb\ccc\ProjectName\module\src\java\...'
			.split(projectName)                                         // ['C:\aa\bbb\ccc\', '\module\src\java\...']
			.firstOrNull()                                              // 'C:\aa\bbb\ccc\'
			?.replace(Separator.Windows.value, Separator.Url.value)     // 'C:/aa/bbb/ccc/'
			?.splitToChain(Separator.Url)                               // ["C:", "aa", "bbb", "ccc"]
			.takeNotNullOrExit()

		private fun findPsiDirectorySubDirectory(selectedDirectory: PsiDirectory, rootDirChain: AnyChain): PsiDirectory {
			var dir: PsiDirectory? = selectedDirectory
			val rootDirPath = Path.of(rootDirChain.castTo(Separator.Url).assemble())
			while (true) {
				val currentDir = dir ?: throw IllegalStateException("PsiDirectory was no found for 'rootDirChain'")

				val dirPath = currentDir.virtualFile.toNioPath()
				if (dirPath == rootDirPath || rootDirPath.contains(dirPath)) { // TODO хз как проверять. надо подебажить. Мб contains не нужен. + чекнуть Separator.Windows
					return currentDir
				}

				dir = currentDir.parentDirectory
			}
		}
	}
}