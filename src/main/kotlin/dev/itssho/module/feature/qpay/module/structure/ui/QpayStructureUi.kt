package dev.itssho.module.feature.qpay.module.structure.ui

import dev.itssho.module.common.component.ktx.scope.ScopeWrapper
import dev.itssho.module.common.component.mvvm.ui.UserInterface
import dev.itssho.module.feature.qpay.module.delegate.mainkt.ui.DataFolderUi
import dev.itssho.module.resources.Strings
import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.modification.Modifier
import dev.itssho.module.uikit.modification.marginStart
import dev.itssho.module.uikit.modification.marginVertical
import dev.itssho.module.uikit.modification.wrapHeight
import dev.itssho.module.uikit.dsl.panel.column
import dev.itssho.module.uikit.dsl.panel.row
import dev.itssho.module.uikit.dsl.select.checkBox
import dev.itssho.module.uikit.dsl.select.comboBox
import dev.itssho.module.uikit.dsl.separator.hDivider
import dev.itssho.module.uikit.dsl.separator.hSpace
import dev.itssho.module.uikit.dsl.separator.vDivider
import org.jetbrains.kotlin.tools.projectWizard.wizard.ui.bordered
import java.awt.Color
import javax.swing.JCheckBox

class QpayStructureUi(override val scope: ScopeWrapper) : UserInterface {

    companion object {
        private const val VERTICAL_MARGIN = 10
        private const val FOLDER_MARGIN = 15
        private const val FILE_MARGIN = FOLDER_MARGIN + 15
    }

    private fun JCheckBox.bindSelection(): JCheckBox = apply {

    }

    private fun JXLinearPanel.domainFoldersGroup() = DataFolderUi()


    private fun JXLinearPanel.dataFoldersGroup() =
        column(Modifier.wrapHeight().marginStart(FOLDER_MARGIN).marginVertical(VERTICAL_MARGIN)) {
            checkBox(text = "data")
            row {
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "datasource").bindSelection()
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "repository").bindSelection()
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "model")
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "converter")
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "network")
            }.bordered()
        }

    private fun JXLinearPanel.presentationFoldersGroup() =
        column(Modifier.wrapHeight().marginStart(FOLDER_MARGIN).marginVertical(VERTICAL_MARGIN)) {
            checkBox(text = "presentation")
            row {
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "converter")
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "model")
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "ViewModel.kt")
            }
        }

    private fun JXLinearPanel.uiFoldersGroup() {
        column(Modifier.wrapHeight().marginStart(FOLDER_MARGIN).marginVertical(VERTICAL_MARGIN)) {
            checkBox(text = "ui")
            row {
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "formatter")
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "Fragment.kt")
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "Adapter.kt")
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "ViewHolder.kt")
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "Router.kt")
            }
        }
    }

    private fun JXLinearPanel.destination() {
        column(Modifier.wrapHeight().marginStart(FOLDER_MARGIN).marginVertical(VERTICAL_MARGIN)) {
            checkBox(text = "Destination.kt")
        }
    }

    private fun JXLinearPanel.unitTests() {
        column(Modifier.wrapHeight().marginStart(FOLDER_MARGIN).marginVertical(VERTICAL_MARGIN)) {
            checkBox(text = "Unit-тесты")
        }
    }

    private fun JXLinearPanel.gradleGroup() {
        column(Modifier.wrapHeight().marginStart(FOLDER_MARGIN).marginVertical(VERTICAL_MARGIN)) {
            checkBox(text = "Gradle файлы")
            row {
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "Модуль в app:build.gradle")
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "Модуль в settings.gradle")
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "Создать <>:build.gradle")
            }
        }
    }

    private fun JXLinearPanel.readMeGroup() {
        row(Modifier.wrapHeight().marginStart(FOLDER_MARGIN).marginVertical(VERTICAL_MARGIN)) {
            checkBox(text = "README.md")
            comboBox(items = arrayOf("ЪУЪ", "Паяльники", "Последний вагон", "Дурка", "Санитары", "Корса", "ДримТим"))
        }
    }

    private fun JXLinearPanel.daggerModulesGroup() {
        column(Modifier.wrapHeight().marginStart(FOLDER_MARGIN).marginVertical(VERTICAL_MARGIN)) {
            checkBox(text = "Dagger модули")
            row {
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "DataModule")
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "<>Module")
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "<>FragmentModule")
                checkBox(Modifier.marginStart(FOLDER_MARGIN), text = "ApiModule")
            }.bordered()
        }
    }

    override fun constructView(basePanel: JXLinearPanel) = basePanel.apply {
        row {
            checkBox(text = Strings.Structure.makeFoldersChB)
            hSpace(15)
            checkBox(text = Strings.Structure.dontMakeFilesChB)
        }
        row {
            column {
                dataFoldersGroup()
                hDivider(Color.BLACK, Modifier.marginStart(60))

                domainFoldersGroup()
                hDivider(Color.BLACK)

                presentationFoldersGroup()
                hDivider(Color.BLACK)

                uiFoldersGroup()
                hDivider(Color.BLACK)

                destination()
            }
            vDivider(Color.BLACK)

            column {
                unitTests()
                hDivider(Color.BLACK)

                gradleGroup()
                hDivider(Color.BLACK)

                readMeGroup()
                hDivider(Color.BLACK)

                daggerModulesGroup()
            }
        }
    }
}