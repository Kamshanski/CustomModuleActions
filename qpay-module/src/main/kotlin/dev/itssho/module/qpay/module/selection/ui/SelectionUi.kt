package dev.itssho.module.qpay.module.selection.ui

import coroutine.observe
import coroutine.observeNotNull
import dev.generateLoopCachedSequence
import dev.itssho.module.component.Scroll
import dev.itssho.module.component.components.simple.JILabel
import dev.itssho.module.component.resources.Icons
import dev.itssho.module.core.actor.JBContext
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
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.ListSelectionModel
import javax.swing.event.ListSelectionEvent

class SelectionUi(
	context: JBContext,
	private val viewModel: SelectionViewModel,
	scope: CoroutineScope,
) : YesNoIdeaDialog<SelectionStepResult>(context = context, scope = scope) {

	companion object {

		private val SCRIPTS = generateLoopCachedSequence(
			listOf(
				ScriptModel.Loading("sad", "loading"),
				ScriptModel.Loaded("sad", "loading", "time"),
				ScriptModel.Failure("sad", "loading", "exeption", "time"),
			),
			emptyList(),
		).also { it.next() }
	}

	private val COLUMN_NAMES = listOf(
		ScriptsTableModel.ColumModel("Скрипт", Any::class.java),
		ScriptsTableModel.ColumModel("Дата", Any::class.java),
		ScriptsTableModel.ColumModel("Статус", Icon::class.java)
	)

	private val tableModel = ScriptsTableModel(COLUMN_NAMES)

	private val searchTitle: JILabel = jiLabel("Выбери скрипт")
	private val centerMessage: JILabel = jiLabel("Скрипты не найдены((")
	private val scriptsTable by lazy { JITable() }
	private val debugButton: JButton = JButton("Thank you, next")
		.also { it.addActionListener { renderScriptsList(SCRIPTS.next()) } }

	override fun constructCenterView(): JComponent =
		table {
			addCell(searchTitle)
			row()
			addCell(debugButton)
			row()

			addCell(Scroll {
				customPanel(BorderLayout()) {
					add(scriptsTable, BorderLayout.CENTER)
//					add(centerMessage, BorderLayout.CENTER)
				}
			}).expand().fill()
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
		centerMessage.isVisible = false
		scriptsTable.isVisible = true
	}

	private fun showCenterMessage() {
		centerMessage.isVisible = true
		scriptsTable.isVisible = false
	}
}