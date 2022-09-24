package dev.itssho.module.feature.qpay.module.name.ui

import dev.itssho.module.common.component.ktx.scope.ScopeWrapper
import dev.itssho.module.common.component.mvvm.ui.UserInterface
import dev.itssho.module.feature.qpay.module.name.presentation.QpayModuleViewModel
import dev.itssho.module.feature.qpay.module.name.presentation.model.ModuleNameValidationResult.*
import dev.itssho.module.uikit.component.panel.JXLinearPanel
import dev.itssho.module.uikit.component.text.TextAlignment
import dev.itssho.module.uikit.dsl.label.label
import dev.itssho.module.uikit.modification.Modifier
import dev.itssho.module.uikit.modification.fillMaxHeight
import dev.itssho.module.uikit.modification.fillMaxWidth
import dev.itssho.module.uikit.modification.weightedHeight
import dev.itssho.module.uikit.dsl.panel.column
import dev.itssho.module.uikit.dsl.panel.row
import dev.itssho.module.uikit.dsl.select.singleSelectionButtonsGroup
import dev.itssho.module.uikit.dsl.text.textField

class QpayModuleNameUi(override val scope: ScopeWrapper) : UserInterface {

    private val viewModel: QpayModuleViewModel = QpayModuleViewModel(scope)
    val delegate = viewModel    // TODO Замениь на делегат

    companion object {
        private const val FOLDER_MARGIN = 15
        private const val FILE_MARGIN = FOLDER_MARGIN + 15
    }

    private fun JXLinearPanel.fullModuleNameInput() =
        textField(Modifier.fillMaxWidth(), text = viewModel.viewState.get().fullModuleName, isEditable = true).apply {
            viewModel.viewState.observe { state ->
                if (state.fullModuleName != text) {
                    text = state.fullModuleName
                }
            }

            addTextChangeListener { _, str ->
                if (viewModel.viewState.get().fullModuleName != str) {
                    viewModel.setFullModuleName(str)
                }
            }
        }

    private fun JXLinearPanel.levelSelectionGroup() {
        column {
            label(text = "Level")

            singleSelectionButtonsGroup {
                for (name in viewModel.levels) {
                    addRadioButton(text = name)
                }
            }.addOnButtonSelectedListener { _, index ->
                viewModel.setSelectedLevel(index)
            }.apply {
                viewModel.viewState.observe { state ->
                    if (state.selectedModuleLevelIndex != selected) {
                        selected = state.selectedModuleLevelIndex
                    }
                }
            }
        }
    }

    private fun JXLinearPanel.domainSelectionGroup() {
        column {
            label(text = "Domain")
            singleSelectionButtonsGroup {
                for (name in viewModel.domains) {
                    addRadioButton(text = name)
                }
            }.addOnButtonSelectedListener { _, index ->
                viewModel.setSelectedDomain(index)
            }.apply {
                viewModel.viewState.observe { state ->
                    if (state.selectedModuleDomainIndex != selected) {
                        selected = state.selectedModuleDomainIndex
                    }
                }
            }
        }
    }

    private fun JXLinearPanel.moduleNameInput() {
        column(Modifier.fillMaxHeight()) {
            label(text = "Module Name")
            textField(Modifier.fillMaxWidth(), viewModel.viewState.get().namePart).apply {
                viewModel.viewState.observe { state ->
                    text = state.namePart
                }
                addTextChangeListener { _, str ->
                    viewModel.setNamePart(str)
                }
            }
        }
    }

    private fun JXLinearPanel.fullModuleNameInputErrorLabel() {
        label(Modifier.fillMaxWidth().weightedHeight(), textAlignment = TextAlignment.RIGHT).apply {
            viewModel.moduleNameValidationError.observe { error ->
                val errorText = when (error) {
                    is Error.Empty            -> "Пустое название модуля недопустимо"
                    is Error.EmptyPart        -> "Название не модет начинаться или заканчиваться на <->"
                    is Error.IllegalCharError -> "Недопустимый символ: разрешены только [a-z][a-z\\d]*"
                    is Warning.MissingParts   -> "Нехватает уровня, домена или названия"
                    is Valid                  -> null
                }

                when (error) {
                    is Error   -> setErrorText(errorText)
                    is Warning -> setWarningText(errorText)
                    is Valid   -> clearText()
                }
            }
        }
    }

    override fun constructView(basePanel: JXLinearPanel) = basePanel.apply {
        fullModuleNameInput()
        fullModuleNameInputErrorLabel()

        row {
            levelSelectionGroup()
            domainSelectionGroup()
            moduleNameInput()
        }

        label(text = "Создать папки и файлы")


    }
}