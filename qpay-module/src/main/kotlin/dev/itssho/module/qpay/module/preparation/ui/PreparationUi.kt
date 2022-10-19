package dev.itssho.module.qpay.module.preparation.ui

import coroutine.observe
import coroutine.observeNotNull
import dev.itssho.module.component.Scroll
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.qpay.module.preparation.presentation.PreparationStepResult
import dev.itssho.module.qpay.module.preparation.presentation.PreparationViewModel
import dev.itssho.module.shared.dialog.ui.idea.YesNoIdeaDialog
import dev.itssho.module.ui.util.constructor.jProgressBar
import dev.itssho.module.ui.util.constructor.jbTextArea
import dev.itssho.module.ui.util.constructor.jiLabel
import dev.itssho.module.ui.util.constructor.table
import kotlinx.coroutines.CoroutineScope
import swing.observeText
import javax.swing.JComponent

class PreparationUi(
	private val viewModel: PreparationViewModel,
	context: JBContext,
	scope: CoroutineScope,
) : YesNoIdeaDialog<PreparationStepResult>(context, scope) {

	private val titleLabel = jiLabel()
	private val descriptionLabel = jiLabel()
	private val progressBar = jProgressBar()
	private val errorText = jbTextArea(editable = false)

	override fun constructCenterView(): JComponent = table {
		addCell(titleLabel).left().expandX().fillX()
		row()

		addCell(descriptionLabel).left().expandX().fillX()
		row()

		addCell(progressBar).expandX().fillX()
		row()

		addCell(Scroll { errorText }).expand().fill()
	}

	override fun configureDialog(dialogWrapper: DummyDialogWrapper) {
		asyncOnUIThread {
			width = 600
			height = 250
			title = "Выполнение скрипта"
		}

		dialogWrapper.okButton.isVisible = false
		dialogWrapper.cancelButton.addActionListener { viewModel.close() }
		initViewModelObservers()

		viewModel.startPreparation()
	}

	private fun initViewModelObservers() {
		viewModel.progress.observe(scope) { progress ->
			if (progress == null) {
				progressBar.isVisible = false
			} else {
				progressBar.isVisible = true
				progressBar.value = progress.completed
				progressBar.minimum = 0
				progressBar.maximum = progress.total
			}
		}

		viewModel.title.observeText(scope, titleLabel)
		viewModel.description.observeText(scope, descriptionLabel)
		viewModel.error.observe(scope) { error -> setError(error) }

		viewModel.finalResult.observeNotNull(scope) { (result, exitNow) ->
			if (exitNow) {
				resumeDialogWithOkAction(result)
			} else {
				setDialogResult(result)
			}
		}
	}

	private fun setError(error: String) {
		if (error.isBlank()) {
			errorText.text = ""
			errorText.isVisible = false
		} else {
			errorText.text = error
			errorText.isVisible = true
		}
	}
}