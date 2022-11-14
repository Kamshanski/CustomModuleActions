package dev.itssho.module.qpay.module.name.deprecated.presentation

import dev.itssho.module.component.chain.Separator
import dev.itssho.module.component.chain.chainOf
import dev.itssho.module.component.chain.splitToChain
import dev.itssho.module.core.presentation.ViewModel
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.qpay.module.name.deprecated.domain.entity.ModuleDomain
import dev.itssho.module.qpay.module.name.deprecated.domain.entity.ModuleLevel
import dev.itssho.module.qpay.module.name.deprecated.presentation.converter.convertDomainName
import dev.itssho.module.qpay.module.name.deprecated.presentation.converter.convertLevelName
import dev.itssho.module.qpay.module.name.deprecated.presentation.converter.convertModuleName
import dev.itssho.module.qpay.module.name.deprecated.presentation.converter.parseModuleName
import dev.itssho.module.qpay.module.name.deprecated.presentation.model.ModuleNameValidationResult
import dev.itssho.module.qpay.module.name.deprecated.presentation.model.ModuleNameValidationResult.Valid
import dev.itssho.module.qpay.module.name.deprecated.presentation.state.ModuleNameState
import dev.itssho.module.qpay.module.name.deprecated.presentation.validator.validateModuleName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.atomic.AtomicBoolean

class QpayNameViewModel(
	private val valueStorage: MutableValueStorage,
) : ViewModel() {

	private companion object {

		val LEVELS = ModuleLevel.values
		val DOMAINS = ModuleDomain.values
	}

	// TODO Решить как-то по-человечески
	private val updateLocked = AtomicBoolean(false)

	private val _moduleNameState = MutableStateFlow(makeInitialViewState())
	val viewState = _moduleNameState as StateFlow<ModuleNameState>

	private val _moduleNameValidationValidationResult: MutableStateFlow<ModuleNameValidationResult> = MutableStateFlow(Valid())
	val moduleNameValidationError = _moduleNameValidationValidationResult as StateFlow<ModuleNameValidationResult>

	private val _finalResult = MutableStateFlow<QpayNameStepResult?>(null)
	val finalResult = _finalResult as StateFlow<QpayNameStepResult?>

	private fun makeInitialViewState(): ModuleNameState {
		val levelIndex = 0
		val domainIndex = 0

		val level = LEVELS[levelIndex].let(::convertLevelName)
		val domain = DOMAINS[domainIndex].let(::convertDomainName)

		val name = ""

		val fullModuleName = Separator.Minus.chainOf(level, domain, name).assemble()

		return ModuleNameState(
			fullModuleName = fullModuleName,
			selectedModuleLevelIndex = levelIndex,
			selectedModuleDomainIndex = domainIndex,
			namePart = name,
		)
	}

	// При выдлении и удалении текста, он не обновляется. Нужно поправить. + почему-то сохранятся предыдущее состояние как-то....
	fun setFullModuleName(name: String) {
		if (updateLocked.get())
			return

		val currentState = viewState.value
		if (currentState.fullModuleName == name)
			return

		val model = parseModuleName(name.splitToChain(Separator.Minus))

		val domainIndex = model.domain?.let { domain -> DOMAINS.indexOf(domain) } ?: -1
		val levelIndex = model.level?.let { level -> LEVELS.indexOf(level) } ?: -1

		val namePart = model.namePart

		setState(
			ModuleNameState(
				fullModuleName = convertModuleName(model).assemble(),
				selectedModuleLevelIndex = levelIndex,
				selectedModuleDomainIndex = domainIndex,
				namePart = namePart,
			)
		)
	}

	fun setSelectedLevel(levelIndex: Int) {
		if (updateLocked.get())
			return

		val rawFullNameChain = viewState.value.fullModuleName.splitToChain(Separator.Minus)
		val model = parseModuleName(rawFullNameChain)

		val newLevel = LEVELS.getOrNull(levelIndex)

		val newModel = model.copy(level = newLevel)

		val newFullName = convertModuleName(newModel).assemble()

		setState(
			viewState.value.copy(
				fullModuleName = newFullName,
				selectedModuleLevelIndex = levelIndex,
			)
		)
	}

	fun setSelectedDomain(domainIndex: Int) {
		if (updateLocked.get())
			return

		val rawFullNameChain = viewState.value.fullModuleName.splitToChain(Separator.Minus)
		val model = parseModuleName(rawFullNameChain)

		val newDomain = DOMAINS.getOrNull(domainIndex)

		val newModel = model.copy(domain = newDomain)

		val newFullName = convertModuleName(newModel).assemble()

		setState(
			viewState.value.copy(
				fullModuleName = newFullName,
				selectedModuleDomainIndex = domainIndex,
			)
		)
	}

	fun setNamePart(namePart: String) {
		if (updateLocked.get())
			return

		val rawFullNameChain = viewState.value.fullModuleName.splitToChain(Separator.Minus)
		val model = parseModuleName(rawFullNameChain)

		val newModel = model.copy(namePart = namePart)

		val newFullName = convertModuleName(newModel).assemble()

		setState(
			viewState.value.copy(
				fullModuleName = newFullName,
				namePart = namePart
			)
		)
	}

	private fun setState(state: ModuleNameState) {
		_moduleNameState.value = state
		_moduleNameValidationValidationResult.value = validateModuleName(state.fullModuleName)
	}

	fun proceed() {
		// TODO сделать проверки. При ошибках не закрывать экран
		val fullModuleName = _moduleNameState.value.fullModuleName
		if (validateModuleName(fullModuleName) is Valid) {
			// TODO Это очень не правильно. Нужно выпилить этот QpayNameStep полностью
			valueStorage.put("MODULE_NAME", fullModuleName)
			_finalResult.value = QpayNameStepResult.Name(fullModuleName)
		}
	}

	fun close() {
		_finalResult.value = QpayNameStepResult.Nothing
	}

	val levels = LEVELS.map { level -> convertLevelName(level) }
	val domains = DOMAINS.map { domain -> convertDomainName(domain) }
}