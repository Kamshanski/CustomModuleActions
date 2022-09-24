package dev.itssho.module.qpay.data.core

import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.LangDataKeys
import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.common.component.chain.splitToChain

@Suppress("UsePropertyAccessSyntax")
class QpayProjectContext(val ideContext: DataContext) {

    val ideView = LangDataKeys.IDE_VIEW.getData(ideContext)!!

    val ideProject = CommonDataKeys.PROJECT.getData(ideContext)!!
    val selectedDirectory = ideView.getOrChooseDirectory()!!

    val projectName = ideProject.name
    val projectParentPath = selectedDirectory.virtualFile.path      // 'C:\aa\bbb\ccc\Project\module\src\java\...'
        .split(projectName)                                         // ['C:\aa\bbb\ccc\', '\module\src\java\...']
        .firstOrNull()                                              // 'C:\aa\bbb\ccc\'
        ?.replace(Separator.Windows.value, Separator.Url.value)     // 'C:/aa/bbb/ccc/'
        ?.splitToChain(Separator.Url)
        ?: throw IllegalStateException("Неудалось распарсить путь до проекта: projectName = $projectName, projectPath = ${selectedDirectory.virtualFile.path}")

    val projectPath = projectParentPath + projectName

    init {
//        FilenameIndex.getFilesByName(ideProject, "settings.gradle", GlobalSearchScope.fileScope())
    }
}