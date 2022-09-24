package dev.itssho.module.common.component.action.ui

import com.intellij.ide.IdeView
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.common.component.chain.splitToChain

@Deprecated("", replaceWith = ReplaceWith("JBContext", "dev.itssho.module.core.actor.JBContext"))
@Suppress("CanBeParameter", "MemberVisibilityCanBePrivate", "UsePropertyAccessSyntax")
class IdeaContext(val ideContext: DataContext): Context {

    val ideView: IdeView = LangDataKeys.IDE_VIEW.getData(ideContext)!!

    val ideProject: Project = CommonDataKeys.PROJECT.getData(ideContext)!!

    /** [PsiDirectory] файла, выбранного сейчас в дереве проекта */
    val selectedDirectory: PsiDirectory = ideView.getOrChooseDirectory()!!

    /** Имя проекта */
    val projectName = ideProject.name

    /** Путь от корня файловой системы ('C:' or '~') до папки Проекта (ИСКЛЮЧИТЕЛЬНО).
     * @sample 'C:\aa\bbb\ccc' */
    val projectRootPath = selectedDirectory.virtualFile.path      // 'C:\aa\bbb\ccc\Project\module\src\java\...'
        .split(projectName)                                         // ['C:\aa\bbb\ccc\', '\module\src\java\...']
        .firstOrNull()                                              // 'C:\aa\bbb\ccc\'
        ?.replace(Separator.Windows.value, Separator.Url.value)     // 'C:/aa/bbb/ccc/'
        ?.splitToChain(Separator.Url)
        ?: throw IllegalStateException("Неудалось распарсить путь до проекта: projectName = $projectName, projectPath = ${selectedDirectory.virtualFile.path}")

    /** Путь от корня файловой системы ('C:' or '~') до папки Проекта (ВКЛЮЧИТЕЛЬНО).
     * @sample 'C:\aa\bbb\ccc\ProjectName' */
    val projectPath = projectRootPath + projectName
}