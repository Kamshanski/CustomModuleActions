package dev.itssho.module.qpay.module.name.presentation

import delegate.unsafeLazy
import dev.itssho.module.core.presentation.ViewModel
import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.name.Issue
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.qpay.module.name.presentation.model.NameStepResult
import dev.itssho.module.qpay.module.name.presentation.validation.NameIssueReporter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NameViewModel(
	private val moduleAction: ModuleAction,
	private val valueStorage: MutableValueStorage,
) : ViewModel() {

	private val _name by unsafeLazy { MutableStateFlow(moduleAction.nameHandler.getInitialName()) }
	val name: StateFlow<String> by unsafeLazy { _name }

	private val _validationIssues = MutableStateFlow<LinkedHashSet<Issue>>(linkedSetOf())
	val validationIssues = _validationIssues as StateFlow<LinkedHashSet<Issue>>

	private val _finalResult = MutableStateFlow<NameStepResult?>(null)
	val finalResult = _finalResult as StateFlow<NameStepResult?>

	fun setName(newName: String) {
		if (name.value == newName) {
			return
		}

		_name.value = newName

		val reporter = NameIssueReporter()
		moduleAction.nameHandler.validate(newName, reporter)

		_validationIssues.value = reporter.issues
	}

	fun proceed() {
		// TODO сделать проверки. При ошибках не закрывать экран
		val fullModuleName = name.value
		moduleAction.nameHandler.handleResult(fullModuleName, valueStorage)
		_finalResult.value = NameStepResult.Name(fullModuleName)
	}

	fun close() {
		_finalResult.value = NameStepResult.Nothing
	}
}