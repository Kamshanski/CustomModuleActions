package dev.itssho.module

import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.InputValidator
import com.intellij.psi.PsiFile
import com.intellij.util.containers.enumMapOf
import dev.itssho.module.project.ProjectModuleCreator
import dev.itssho.module.project.ProjectNameConverter
import dev.itssho.module.project.ProjectType
import dev.itssho.module.project.ProjectTypeConverter
import javax.swing.Icon

fun showModuleCreatorDialog(
    ideProject: Project,
    title: String,
    errorTitle: String,
    projectTypeConverter: ProjectTypeConverter,
    projectNameConverter: ProjectNameConverter,
    commonModuleNameValidator: InputValidator? = null,
    builder: ModuleCreatorDialogNonBlockingPopupBuilder.() -> Unit,
) {
    ModuleCreatorDialogNonBlockingPopupBuilder(
        ideProject = ideProject,
        title = title,
        errorTitle = errorTitle,
        projectTypeConverter = projectTypeConverter,
        projectNameConverter = projectNameConverter,
        commonModuleNameValidator = commonModuleNameValidator,
    )
        .apply(builder)
        .show()
}

class ModuleCreatorDialogNonBlockingPopupBuilder(
    val ideProject: Project,
    val title: String,
    val errorTitle: String,
    val projectTypeConverter: ProjectTypeConverter,
    val projectNameConverter: ProjectNameConverter,
    val commonModuleNameValidator: InputValidator? = null,
) {
    private val builder by lazy { CreateFileFromTemplateDialog.createDialog(ideProject) }
    private val moduleCreators = enumMapOf<ProjectType, ProjectModuleCreator>()

    private var isShowed = false

    private fun checkDialogNeverShown() = require(!isShowed) { "Dialog is already shown. It 's not reusable. Create new instance to show new dialog" }

    fun addProject(
        project: ProjectType,
        icon: Icon,
        moduleCreator: ProjectModuleCreator,
        nameValidator: InputValidator? = null,
    ): ModuleCreatorDialogNonBlockingPopupBuilder = apply {
        checkDialogNeverShown()
        val name = projectNameConverter.convert(project)
        val type = projectTypeConverter.convert(project)
        moduleCreators[project] = moduleCreator
//        builder.addKind(name, icon, type, nameValidator) TODO
    }

    fun show() {
        checkDialogNeverShown()
        isShowed = true

        builder.setTitle(title)

        builder.setValidator(commonModuleNameValidator)

        builder.show(errorTitle, null, getFileCreator()) {}
    }

    private fun getFileCreator() = object : CreateFileFromTemplateDialog.FileCreator<PsiFile> {
        override fun createFile(moduleName: String, projectType: String): PsiFile? {
            val project = projectTypeConverter.revert(projectType)

            moduleCreators[project]?.create(ideProject, moduleName) ?: error("В мапе проектов не нашлось ProjectModuleCreator для проекта $project")

            return null
        }

        override fun getActionName(name: String, templateName: String): String = actionName

        override fun startInWriteAction(): Boolean = true
    }


    fun getCustomProperties(): MutableMap<String, String>? =
        builder.customProperties
}