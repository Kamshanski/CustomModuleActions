@file:Suppress("UsePropertyAccessSyntax")

package dev.itssho.module

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.project.DumbAware
import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.common.component.chain.splitToChain
import dev.itssho.module.project.ProjectNameConverter
import dev.itssho.module.project.ProjectType
import dev.itssho.module.project.ProjectTypeConverter
import dev.itssho.module.project.qpay.QpayModuleCreator
import dev.itssho.module.project.qpay.QpayNameValidator
import dev.itssho.module.resources.R
import javax.swing.Icon

val actionName: String = "NewModule"
val actionDescription: String = "Make new module here"
val actionIcon: Icon = AllIcons.Modules.UnloadedModule

class ModuleCreatorAction(
) : AnAction(
    actionName,
    actionDescription,
    actionIcon
), DumbAware {

    private val projectTypeConverter = ProjectTypeConverter()

    override fun actionPerformed(e: AnActionEvent) {
        val dataContext = e.dataContext
        val view = LangDataKeys.IDE_VIEW.getData(dataContext) ?: return

        val project = CommonDataKeys.PROJECT.getData(dataContext) ?: return
        val dir = view.getOrChooseDirectory() ?: return

        val projectName = project.name
        val projectRootPathChain = dir.virtualFile.path                 // 'C:\aa\bbb\ccc\Project\module\src\java\...'
            .split(projectName)                                         // ['C:\aa\bbb\ccc\', '\module\src\java\...']
            .firstOrNull()                                              // 'C:\aa\bbb\ccc\'
            ?.replace(Separator.Windows.value, Separator.Url.value)     // 'C:/aa/bbb/ccc/'
            ?.splitToChain(Separator.Url)
            ?: throw IllegalStateException("Неудалось распарсить путь до проекта: projectName = $projectName, projectPath = ${dir.virtualFile.path}")

        val modulesPathRootChain = projectRootPathChain + projectName

        showModuleCreatorDialog(
            ideProject = project,
            title = R.string.title,
            errorTitle = R.string.errorTitle,
            projectTypeConverter = ProjectTypeConverter(),
            projectNameConverter = ProjectNameConverter(),
            commonModuleNameValidator = null,
        ) {
            addProject(
                project = ProjectType.QPay,
                icon = R.icon.qpay,
                moduleCreator = QpayModuleCreator(modulesPathRootChain),
                nameValidator = QpayNameValidator(),
            )
            addProject(
                project = ProjectType.Party,
                icon = R.icon.empty,
                moduleCreator = QpayModuleCreator(modulesPathRootChain),
                nameValidator = QpayNameValidator(),
            )
            addProject(
                project = ProjectType.Recycle,
                icon = R.icon.empty,
                moduleCreator = QpayModuleCreator(modulesPathRootChain),
                nameValidator = QpayNameValidator(),
            )
        }
    }
}


/*
//    private fun showCreateModuleDialogForPsiEntries(callback: (ModuleKit, String) -> Unit) {
//        val factory = JBPopupFactory.getInstance()
//        val step = BaseListPopupStep("Tree", "Yuii")
//        factory.createListPopup(step).addListSelectionListener {
//            callback(ModuleKit.values()[it.firstIndex], "ssd")
//        }
//
//        object : DialogWrapper(true) {
//            var moduleName = ""
//            val buttons = mutableListOf<JButton>()
//            override fun createCenterPanel(): JComponent {
//                return JPanel().apply {
//                    BoxLayout(this, BoxLayout.X_AXIS).apply {
//                        JTextField(moduleName, 0).apply {
//                            document.addUndoableEditListener {
//                                moduleName = text
//                            }
//                        }.apply { add(this) }
//                        for (kit in ModuleKit.values()) {
//                            add(JButton(kit.name).apply { addActionListener { callback(kit, moduleName); close(0) } })
//                        }
//                    }
//                }
//                panel {
//                    row("Enter name:") {
//                        textField(getter = { moduleName }, setter = { name -> updateModuleName(name) })
//                    }
//
//                    for (kit in ModuleKit.values()) {
//                        row {
//                            button(kit.name) {
//                                moduleKit = kit
//                                exitDialog()
//                            }
//                                .also { buttons.add(it.component) }
//                        }
//                    }
//                }
//            }
//
//            fun updateModuleName(newName: String) {
//                invokeLater {
//                    moduleName = newName
//
//                    val isNameFilled = moduleName.isNotBlank()
//                    if (isNameFilled) {
//                        buttons.forEach { it.isEnabled = isNameFilled }
//                    }
//                }
//            }
//        }.showAndGet()
//    }
 */