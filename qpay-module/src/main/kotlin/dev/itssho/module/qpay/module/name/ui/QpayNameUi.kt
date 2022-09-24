package dev.itssho.module.qpay.module.name.ui

import coroutine.observe
import dev.itssho.module.component.components.select.ButtonCollection
import dev.itssho.module.component.value.Gravity
import dev.itssho.module.core.ui.UserInterface
import dev.itssho.module.qpay.module.name.presentation.QpayNameViewModel
import dev.itssho.module.qpay.module.name.presentation.model.ModuleNameValidationResult.*
import dev.itssho.module.ui.util.constructor.*
import dev.itssho.module.ui.util.container.cellsLeft
import dev.itssho.module.ui.util.container.cellsTop
import dev.itssho.module.ui.util.container.padHorizontal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.swing.JComponent

class QpayNameUi(
	private val viewModel: QpayNameViewModel,
	scope: CoroutineScope,
) : UserInterface(scope) {

	private val fullModuleNameInput = jiTextField(text = viewModel.viewState.value.fullModuleName, editable = true)
	private val fullModuleNameInputErrorLabel = jiLabel(textAlignment = Gravity.END)
	private val levelCheckBoxCollection: ButtonCollection = buttonsCollection(viewModel.levels.map { level -> jCheckBox(text = level) })
	private val domainCheckBoxCollection: ButtonCollection = buttonsCollection(viewModel.domains.map { domain -> jCheckBox(text = domain) })
	private val moduleNameInput = jiTextField(viewModel.viewState.value.namePart, editable = true)
//		.apply { border = BasicBorders.MarginBorder() } // Удаляет кромку вокруг поля ввода

	private fun composeLayout(): JComponent {
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

	private fun initViewModelObserve() {
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
				is Failure.Empty        -> "Пустое название модуля недопустимо"
				is Failure.EmptyPart    -> "Название не может начинаться или заканчиваться на <->"
				is Failure.IllegalChar  -> "Недопустимый символ: паттерн частей имени [a-z][a-z\\d]*"
				is Warning.MissingParts -> "Нехватает уровня, домена или названия"
				is Valid                -> null
			}

			fullModuleNameInputErrorLabel.apply {
				when (error) {
					is Failure -> setErrorText(errorText)
					is Warning -> setWarningText(errorText)
					is Valid   -> clearText()
				}
			}
		}
	}

	private fun initViewListening() {
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

	override fun constructView(): JComponent =
		composeLayout().also {
			initViewModelObserve()
			initViewListening()
		}

	override fun finish() {
		scope.cancel()
	}
}