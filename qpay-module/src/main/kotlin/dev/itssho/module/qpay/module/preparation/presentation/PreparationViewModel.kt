package dev.itssho.module.qpay.module.preparation.presentation

import dev.itssho.module.core.presentation.ViewModel
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.qpay.module.common.domain.usecase.GetModuleActionUseCase
import dev.itssho.module.qpay.module.common.domain.usecase.LoadModuleActionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PreparationViewModel(
	private val valueStorage: MutableValueStorage,
	private val loadModuleActionUseCase: LoadModuleActionUseCase,
	private val getModuleActionUseCase: GetModuleActionUseCase,
) : ViewModel() {

	private val phases = listOf(
		PreparationPhase.SCRIPT_LOAD,
		PreparationPhase.VALUES_INIT,
	)

	private val _progress: MutableStateFlow<PreparationProgress?> = MutableStateFlow(PreparationProgress(0, phases.lastIndex))
	val progress: StateFlow<PreparationProgress?> = _progress

	private val _title: MutableStateFlow<String> = MutableStateFlow("")
	val title: StateFlow<String> = _title

	private val _description: MutableStateFlow<String> = MutableStateFlow("")
	val description: StateFlow<String> = _description

	private val _error: MutableStateFlow<String> = MutableStateFlow("")
	val error: StateFlow<String> = _error

	private val _finalResult = MutableStateFlow<PreparationStepResult?>(null)
	val finalResult = _finalResult as StateFlow<PreparationStepResult?>

	// TODO унести в ресурсы
	fun startPreparation() {
		launch {
			try {
				phases.forEachIndexed { i, phase ->
					when (phase) {
						PreparationPhase.SCRIPT_LOAD -> loadScript()
						PreparationPhase.VALUES_INIT -> initValues()
					}
					_progress.value = progress.value!!.copy(completed = i + 1)
				}

				_finalResult.value = PreparationStepResult.Success
			} catch (ex: Exception) {
				_error.value = ex.toString()
				_description.value = "На этом шаге произошла ошибка"
				_progress.value = null
			}
		}
	}

	private suspend fun loadScript() {
		// TODO унести в ресурсы
		showPhaseDescription(title = "Загрузка скрипта", description = "Пододи")

		loadModuleActionUseCase()
	}

	private fun initValues() {
		// TODO унести в ресурсы
		showPhaseDescription(title = "Инициализация переменных", description = "Пододи ещё немного")

		val moduleAction = getModuleActionUseCase()
		val initializer = moduleAction.valuesInitializer
		initializer.initialize(valueStorage)
	}

	private fun showPhaseDescription(title: String, description: String) {
		_title.value = title
		_description.value = description
	}

	fun close() {
		_finalResult.value = PreparationStepResult.Failure
	}
}