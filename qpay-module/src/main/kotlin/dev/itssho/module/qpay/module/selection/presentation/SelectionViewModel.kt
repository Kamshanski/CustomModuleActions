package dev.itssho.module.qpay.module.selection.presentation

import chrono.SIMPLE_DATE_TIME_FORMATTER
import coroutine.DuplicableMutableStateFlow
import coroutine.gather
import dev.itssho.module.core.presentation.ViewModel
import dev.itssho.module.qpay.module.selection.domain.usecase.GetModuleActionByScriptPathUseCase
import dev.itssho.module.qpay.module.selection.domain.usecase.InitializeModuleActionUseCase
import dev.itssho.module.service.action.module.domain.entity.Script
import dev.itssho.module.service.action.module.domain.usecase.UpdateScriptsUseCase
import fullStackTraceString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import java.nio.file.Path

class SelectionViewModel(
	private val updateScriptsUseCase: UpdateScriptsUseCase,
	private val getModuleActionByScriptPathUseCase: GetModuleActionByScriptPathUseCase,
	private val initializeModuleActionUseCase: InitializeModuleActionUseCase,
) : ViewModel() {

	private val _finalResult = MutableStateFlow<SelectionStepResult?>(null)
	val finalResult = _finalResult as StateFlow<SelectionStepResult?>

	private val _state: MutableStateFlow<List<ScriptModel>> = DuplicableMutableStateFlow(emptyList())
	val state: StateFlow<List<ScriptModel>> = _state

	private val _mayProceed: MutableStateFlow<Boolean> = MutableStateFlow(false)
	val mayProceed = _mayProceed as StateFlow<Boolean>

	fun loadScripts() {
		launch {
			updateScriptsUseCase()
				.map { scripts -> scripts.map { convertScriptToScriptModel(it) } }
				.map { scripts -> scripts.sortedBy { it.name } }
				.gather { _state.value = it }
		}
	}

	fun handleScriptClick(scriptIndex: Int) {
		handleScriptSelection(scriptIndex)

		val scriptModels = _state.value
		val selectedScript = scriptModels.getOrNull(scriptIndex)

		if (selectedScript != null) {
			if (selectedScript is ScriptModel.Loaded) {
				if (mayProceed.value) {
					val moduleAction = getModuleActionByScriptPathUseCase(selectedScript.path)

					initializeModuleActionUseCase(moduleAction)

					_finalResult.value = SelectionStepResult.Compilation(moduleAction)
				}
			}
		}
	}

	fun handleScriptSelection(scriptIndex: Int) {
		val scriptSelected = scriptIndex in _state.value.indices
		_mayProceed.value = scriptSelected
	}

	fun close() {
		_finalResult.value = SelectionStepResult.Nothing
	}
}

private fun convertScriptToScriptModel(script: Script): ScriptModel =
	script.run {
		val scriptName = Path.of(path).lastOrNull()?.toString().orEmpty()
		when (this) {
			is Script.Failure -> ScriptModel.Failure(path, scriptName, exception.fullStackTraceString(), timestamp.format(SIMPLE_DATE_TIME_FORMATTER))
			is Script.Loaded  -> ScriptModel.Loaded(path, scriptName, timestamp.format(SIMPLE_DATE_TIME_FORMATTER))
			is Script.Loading -> ScriptModel.Loading(path, scriptName)
		}
	}
