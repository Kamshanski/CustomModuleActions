package dev.itssho.module.qpay.module.name.deprecated.ui

import coroutine.observe
import dev.itssho.module.component.components.select.ButtonCollection
import dev.itssho.module.component.resources.Strings
import dev.itssho.module.component.value.Gravity
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.qpay.module.name.deprecated.presentation.QpayNameStepResult
import dev.itssho.module.qpay.module.name.deprecated.presentation.QpayNameViewModel
import dev.itssho.module.qpay.module.name.deprecated.presentation.model.ModuleNameValidationResult
import dev.itssho.module.shared.dialog.ui.idea.YesNoIdeaDialog
import dev.itssho.module.ui.util.constructor.buttonsCollection
import dev.itssho.module.ui.util.constructor.jCheckBox
import dev.itssho.module.ui.util.constructor.jiLabel
import dev.itssho.module.ui.util.constructor.jiTextField
import dev.itssho.module.ui.util.constructor.table
import dev.itssho.module.ui.util.constructor.tableColumn
import dev.itssho.module.ui.util.container.cellsLeft
import dev.itssho.module.ui.util.container.cellsTop
import dev.itssho.module.ui.util.container.padHorizontal
import kotlinx.coroutines.CoroutineScope
import javax.swing.JComponent

class NameUI(
	context: JBContext,
	private val viewModel: QpayNameViewModel,
	scope: CoroutineScope,
) : YesNoIdeaDialog<QpayNameStepResult>(context = context, scope = scope) {

	private val fullModuleNameInput = jiTextField(text = viewModel.viewState.value.fullModuleName, editable = true)
	private val fullModuleNameInputErrorLabel = jiLabel(textAlignment = Gravity.END)
	private val levelCheckBoxCollection: ButtonCollection = buttonsCollection(viewModel.levels.map { level -> jCheckBox(text = level) })
	private val domainCheckBoxCollection: ButtonCollection = buttonsCollection(viewModel.domains.map { domain -> jCheckBox(text = domain) })
	private val moduleNameInput = jiTextField(viewModel.viewState.value.namePart, editable = true)

	override fun constructCenterView(): JComponent {

		return table {
			addCell(fullModuleNameInput).expandX().fillX().colspan(3)
			row()

			addCell(fullModuleNameInputErrorLabel).expandX().right().colspan(3)
			row()

			addCell(
				tableColumn(
					jiLabel(text = "Level"),
					*levelCheckBoxCollection.buttons.toTypedArray(),
				).cellsTop().cellsLeft()
			).expandY().top()

			addCell(
				tableColumn(
					jiLabel(text = "Domain"),
					*domainCheckBoxCollection.buttons.toTypedArray(),
				).cellsTop().cellsLeft()
			).expandY().top().padHorizontal(8f)

			// TODO Починить Баг. Если печатать в середине имени, то курсор насильно перекидывает в начало
			addCell(table {
				addCell(jiLabel(text = "Subdomain/Name")).left()
				row()
				addCell(moduleNameInput).expandX().fillX()
			}).expandY().top().left().expandX().fillX()
		}.pad(15f)
	}

	override fun configureDialog(dialogWrapper: DummyDialogWrapper) {
		asyncOnUIThread {
			title = Strings.Name.title
			width = 500
			height = 500
		}

		dialogWrapper.okButton.addActionListener {
			viewModel.proceed()
		}
		dialogWrapper.cancelButton.addActionListener {
			viewModel.close()
		}

		initViewModelObservers()
		initViewsListeners()
	}

	private fun initViewModelObservers() {
		viewModel.viewState.observe(scope) { state ->
			fullModuleNameInput.apply {
				if (state.fullModuleName != text) {
					text = state.fullModuleName
				}
			}

			levelCheckBoxCollection.apply {
				if (state.selectedModuleLevelIndex != selectedIndex) {
					selectedIndex = state.selectedModuleLevelIndex
				}
			}

			domainCheckBoxCollection.apply {
				if (state.selectedModuleDomainIndex != selectedIndex) {
					selectedIndex = state.selectedModuleDomainIndex
				}
			}

			moduleNameInput.text = state.namePart
		}

		viewModel.moduleNameValidationError.observe(scope) { error ->
			val errorText = when (error) {
				is ModuleNameValidationResult.Failure.Empty        -> "Пустое название модуля недопустимо"
				is ModuleNameValidationResult.Failure.EmptyPart    -> "Название не может начинаться или заканчиваться на <->"
				is ModuleNameValidationResult.Failure.IllegalChar  -> "Недопустимый символ: паттерн частей имени [a-z][a-z\\d]*"
				is ModuleNameValidationResult.Warning.MissingParts -> "Нехватает уровня, домена или названия"
				is ModuleNameValidationResult.Valid                -> null
			}

			fullModuleNameInputErrorLabel.apply {
				when (error) {
					is ModuleNameValidationResult.Failure -> setErrorText(errorText)
					is ModuleNameValidationResult.Warning -> setWarningText(errorText)
					is ModuleNameValidationResult.Valid   -> clearText()
				}
			}
		}

		viewModel.finalResult.observe(scope) { result ->
			result ?: return@observe
			when (result) {
				is QpayNameStepResult.Name    -> resumeDialogWithOkAction(result)
				is QpayNameStepResult.Nothing -> resumeDialogWithCancelAction(result)
			}
		}
	}

	private fun initViewsListeners() {
		fullModuleNameInput.addTextChangeListener { str ->
			if (viewModel.viewState.value.fullModuleName != str) {
				viewModel.setFullModuleName(str)
			}
		}

		levelCheckBoxCollection.addOnButtonSelectedListener { btn, index ->
			viewModel.setSelectedLevel(index)
		}

		domainCheckBoxCollection.addOnButtonSelectedListener { btn, index ->
			viewModel.setSelectedDomain(index)
		}

		moduleNameInput.addTextChangeListener { str ->
			viewModel.setNamePart(str)
		}
	}
}