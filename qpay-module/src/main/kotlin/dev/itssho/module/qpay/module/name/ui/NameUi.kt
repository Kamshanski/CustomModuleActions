package dev.itssho.module.qpay.module.name.ui

import coroutine.observe
import dev.itssho.module.component.Scroll
import dev.itssho.module.component.resources.Strings
import dev.itssho.module.component.value.Gravity
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.hierarchy.name.Issue
import dev.itssho.module.qpay.module.name.presentation.NameViewModel
import dev.itssho.module.qpay.module.name.presentation.model.NameStepResult
import dev.itssho.module.shared.dialog.ui.idea.YesNoIdeaDialog
import dev.itssho.module.ui.util.constructor.jiLabel
import dev.itssho.module.ui.util.constructor.jiTextField
import dev.itssho.module.ui.util.constructor.table
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import string.escapeHtml
import javax.swing.JComponent

class NameUi(
	context: JBContext,
	private val viewModel: NameViewModel,
	scope: CoroutineScope,
) : YesNoIdeaDialog<NameStepResult>(context = context, scope = scope) {

	private val nameInputGuideLabel = jiLabel()
	private val moduleNameInput = jiTextField(editable = true)
	private val validationIssuesLabel = jiLabel(textAlignment = Gravity.TOP_START)

	override fun constructCenterView(): JComponent {
		return table {
			addCell(nameInputGuideLabel).expandX().fillX().top()
			row()

			addCell(moduleNameInput).expandX().fillX().top()
			row()

			addCell(Scroll(horizontal = false) { validationIssuesLabel }).expand().fill()
		}
			.pad(6f)
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
		initViews()
		initViewsListeners()
	}

	private fun initViewModelObservers() {
		viewModel.name.observe(scope) { name ->
			moduleNameInput.apply {
				if (name != text) {
					text = name
				}
			}
		}

		viewModel.validationIssues.observe(scope) { issues ->
			val issuesListText = makeIssuesListText(issues)
			validationIssuesLabel.text = issuesListText
		}

		viewModel.finalResult.observe(scope) { result ->
			result ?: return@observe
			when (result) {
				is NameStepResult.Name    -> resumeDialogWithOkAction(result)
				is NameStepResult.Nothing -> resumeDialogWithCancelAction(result)
			}
		}
	}

	private fun makeIssuesListText(issues: Collection<Issue>): String {
		val builder = StringBuilder()

		builder.append("<html>")
		issues.joinTo(builder, separator = "\n") { issue ->
			val color = when (issue) {
				is Issue.Error   -> "red"
				is Issue.Warning -> "orange"
			}
			"<p style=\"color:$color;\">${issue.message.escapeHtml()}</p>"
		}
		builder.append("</html>")

		return builder.toString()
	}

	private fun initViewsListeners() {
		moduleNameInput.addTextChangeListener { name -> viewModel.setName(name) }
	}

	private fun initViews() {
		nameInputGuideLabel.text = "Введите имя модуля"
	}

	override fun onFinish() {
		super.onFinish()
		viewModel.finish()
		scope.cancel()
	}
}