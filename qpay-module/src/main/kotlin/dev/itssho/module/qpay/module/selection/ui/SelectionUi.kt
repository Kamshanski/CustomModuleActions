package dev.itssho.module.qpay.module.selection.ui

import coroutine.observe
import coroutine.observeNotNull
import dev.itssho.module.component.Scroll
import dev.itssho.module.component.components.simple.JILabel
import dev.itssho.module.component.resources.Icons
import dev.itssho.module.component.value.Gravity
import dev.itssho.module.core.context.ProjectWindowClickContext
import dev.itssho.module.qpay.module.selection.presentation.ScriptModel
import dev.itssho.module.qpay.module.selection.presentation.SelectionStepResult
import dev.itssho.module.qpay.module.selection.presentation.SelectionViewModel
import dev.itssho.module.shared.dialog.ui.idea.YesNoIdeaDialog
import dev.itssho.module.ui.util.constructor.jiLabel
import dev.itssho.module.ui.util.constructor.table
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.kotlin.tools.projectWizard.wizard.ui.customPanel
import java.awt.BorderLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.Icon
import javax.swing.JComponent
import javax.swing.ListSelectionModel
import javax.swing.event.ListSelectionEvent

class SelectionUi(
	context: ProjectWindowClickContext,
	private val viewModel: SelectionViewModel,
	scope: CoroutineScope,
) : YesNoIdeaDialog<SelectionStepResult>(context = context, scope = scope) {

	private val COLUMN_NAMES = listOf(
		ScriptsTableModel.ColumModel("Скрипт", Any::class.java),
		ScriptsTableModel.ColumModel("Дата", Any::class.java),
		ScriptsTableModel.ColumModel("Статус", Icon::class.java)
	)

	private val tableModel = ScriptsTableModel(COLUMN_NAMES)

	private val searchTitle: JILabel = jiLabel("Выбери скрипт")
	private val centerPanel = customPanel(BorderLayout()) { }
	private val centerMessage: JILabel = jiLabel(" ", textAlignment = Gravity.CENTER)
	private val scriptsTable by lazy { JITable() }

	override fun constructCenterView(): JComponent =
		table {
			addCell(searchTitle).left()
			row()

			addCell(
				Scroll { centerPanel }
			).expand().fill()
		}

	override fun configureDialog(dialogWrapper: DummyDialogWrapper) {
		asyncOnUIThread {
			title = "Выберите скрипт"
			width = 500
			height = 500
		}

		dialogWrapper.okButton.addActionListener {
			val selectedRowIndex = scriptsTable.selectedRow
			viewModel.handleScriptClick(selectedRowIndex)
		}
		dialogWrapper.cancelButton.addActionListener {
			viewModel.close()
		}

		initViews()
		initViewModelObservers(dialogWrapper)
		initViewsListeners()

		viewModel.loadScripts()
	}

	private fun initViews() {
		showCenterMessage()

		scriptsTable.cellEditor
		scriptsTable.model = tableModel
		scriptsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
		scriptsTable.rowSelectionAllowed = true
		scriptsTable.columnSelectionAllowed = false
		scriptsTable.cellSelectionEnabled = false
	}

	private fun initViewModelObservers(dialogWrapper: DummyDialogWrapper) {
		viewModel.state.observe(scope) { scriptModels ->
			renderScriptsList(scriptModels)
		}

		viewModel.mayProceed.observe(scope) { mayProceed ->
			dialogWrapper.okButton.isEnabled = mayProceed
		}

		viewModel.finalResult.observeNotNull(scope) { result ->
			when (result) {
				is SelectionStepResult.Compilation -> resumeDialogWithOkAction(result)
				is SelectionStepResult.Nothing     -> resumeDialogWithCancelAction(result)
			}
		}
	}

	private fun renderScriptsList(scriptModels: List<ScriptModel>) {
		val scriptsCount = scriptModels.size

		if (scriptsCount < 1) {
			showCenterMessage()

			centerMessage.text = "Скрипты не найдены(("

			tableModel.rowCount = 0
		} else {
			showTable()

			tableModel.rowCount = scriptModels.size
			scriptModels.forEachIndexed { index, model ->
				tableModel.setValueAt(model.name, index, 0)
				when (model) {
					is ScriptModel.Failure -> {
						tableModel.setValueAt(model.timestamp, index, 1)
						tableModel.setValueAt(Icons.ERROR_RED.ic12px, index, 2)
					}

					is ScriptModel.Loaded  -> {
						tableModel.setValueAt(model.timestamp, index, 1)
						tableModel.setValueAt(Icons.SUCCESS_GREEN.ic12px, index, 2)
					}

					is ScriptModel.Loading -> {
						tableModel.setValueAt("", index, 1)
						tableModel.setValueAt(Icons.WAIT_YELLOW.ic12px, index, 2)
					}
				}
			}
		}
	}

	private fun initViewsListeners() {
		scriptsTable.addMouseListener(object : MouseAdapter() {
			override fun mouseClicked(e: MouseEvent) {
				if (e.clickCount == 2) {
					val table = e.source as JITable
					val point = e.point
					val row = table.rowAtPoint(point)
					if (table.selectedRow != -1) {
						viewModel.handleScriptClick(row)
					}
				}
			}
		})

		scriptsTable.selectionModel.addListSelectionListener { event: ListSelectionEvent ->
			val selectedRowIndex = scriptsTable.selectedRow
			viewModel.handleScriptSelection(selectedRowIndex)
		}
	}

	private fun showTable() {
		centerPanel.removeAll()
		centerPanel.add(scriptsTable, BorderLayout.CENTER)
	}

	private fun showCenterMessage() {
		centerPanel.removeAll()
		centerPanel.add(centerMessage, BorderLayout.CENTER)
	}
}