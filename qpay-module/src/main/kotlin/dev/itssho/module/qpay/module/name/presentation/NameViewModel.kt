package dev.itssho.module.qpay.module.name.presentation

import delegate.unsafeLazy
import dev.itssho.module.core.presentation.ViewModel
import dev.itssho.module.hierarchy.name.Issue
import dev.itssho.module.qpay.module.name.domain.usecase.GetInitialNameUseCase
import dev.itssho.module.qpay.module.name.domain.usecase.SubmitModuleNameUseCase
import dev.itssho.module.qpay.module.name.domain.usecase.ValidateModuleNameUseCase
import dev.itssho.module.qpay.module.name.presentation.model.NameStepResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NameViewModel(
	private val getInitialNameUseCase: GetInitialNameUseCase,
	private val validateModuleNameUseCase: ValidateModuleNameUseCase,
	private val submitModuleNameUseCase: SubmitModuleNameUseCase,
) : ViewModel() {

	private val _name by unsafeLazy { MutableStateFlow(getInitialNameUseCase()) }
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

		val issues = validateModuleNameUseCase(newName)

		_validationIssues.value = issues
	}

	fun proceed() {
		// TODO сделать проверки. При ошибках не закрывать экран
		val fullModuleName = name.value
		val issues = submitModuleNameUseCase(fullModuleName)
		if (issues.isEmpty()) {
			_finalResult.value = NameStepResult.Name(fullModuleName)
		} else {
			_validationIssues.value = issues
		}
	}

	fun close() {
		_finalResult.value = NameStepResult.Nothing
	}
}