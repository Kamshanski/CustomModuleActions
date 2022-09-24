package dev.itssho.module.feature.qpay.module.name.presentation

import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.common.component.chain.chainOf
import dev.itssho.module.common.component.chain.splitToChain
import dev.itssho.module.common.component.ktx.scope.ScopeWrapper
import dev.itssho.module.common.component.mvvm.presentation.ViewModel
import dev.itssho.module.common.component.value.Var
import dev.itssho.module.common.component.value.asVal
import dev.itssho.module.common.component.value.setValueWithLock
import dev.itssho.module.feature.qpay.module.domain.entity.path.ModuleDomain
import dev.itssho.module.feature.qpay.module.domain.entity.path.ModuleLevel
import dev.itssho.module.feature.qpay.module.name.presentation.converter.convertDomainName
import dev.itssho.module.feature.qpay.module.name.presentation.converter.convertLevelName
import dev.itssho.module.feature.qpay.module.name.presentation.converter.convertModuleName
import dev.itssho.module.feature.qpay.module.name.presentation.converter.parseModuleName
import dev.itssho.module.feature.qpay.module.name.presentation.model.ModuleNameValidationResult
import dev.itssho.module.feature.qpay.module.name.presentation.model.ModuleNameValidationResult.Valid
import dev.itssho.module.feature.qpay.module.name.presentation.state.ModuleNameState
import dev.itssho.module.feature.qpay.module.name.presentation.validator.validateModuleName
import java.util.concurrent.atomic.AtomicBoolean

class QpayModuleViewModel(scope: ScopeWrapper) : ViewModel(scope) {

    private companion object {

        val LEVELS = ModuleLevel.values
        val DOMAINS = ModuleDomain.values
    }

    private val updateLocked = AtomicBoolean(false)

    private val _moduleNameState: Var<ModuleNameState> = varOf(makeInitialViewState())
    val viewState = _moduleNameState.asVal()

    private val _moduleNameValidationValidationResult: Var<ModuleNameValidationResult> = varOf(Valid())
    val moduleNameValidationError = _moduleNameValidationValidationResult.asVal()

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

        val currentState = viewState.get()
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

        val rawFullNameChain = viewState.get().fullModuleName.splitToChain(Separator.Minus)
        val model = parseModuleName(rawFullNameChain)

        val newLevel = LEVELS.getOrNull(levelIndex)

        val newModel = model.copy(level = newLevel)

        val newFullName = convertModuleName(newModel).assemble()

        setState(
            viewState.get().copy(
                fullModuleName = newFullName,
                selectedModuleLevelIndex = levelIndex,
            )
        )
    }

    fun setSelectedDomain(domainIndex: Int) {
        if (updateLocked.get())
            return

        val rawFullNameChain = viewState.get().fullModuleName.splitToChain(Separator.Minus)
        val model = parseModuleName(rawFullNameChain)

        val newDomain = DOMAINS.getOrNull(domainIndex)

        val newModel = model.copy(domain = newDomain)

        val newFullName = convertModuleName(newModel).assemble()

        setState(
            viewState.get().copy(
                fullModuleName = newFullName,
                selectedModuleDomainIndex = domainIndex,
            )
        )
    }

    fun setNamePart(namePart: String) {
        if (updateLocked.get())
            return

        val rawFullNameChain = viewState.get().fullModuleName.splitToChain(Separator.Minus)
        val model = parseModuleName(rawFullNameChain)

        val newModel = model.copy(namePart = namePart)

        val newFullName = convertModuleName(newModel).assemble()

        setState(
            viewState.get().copy(
                fullModuleName = newFullName,
                namePart = namePart
            )
        )
    }

    // TODO Добавить функцию валидации символов для полного имени модуля и именной части. Т.е. буквы маленькие, тире и цифры (но не в начале слова)
    fun fitFullNameToConstraints(input: String): String {
        return input
    }

    private fun setState(state: ModuleNameState) {
        _moduleNameState.setValueWithLock(state, updateLocked)
        _moduleNameValidationValidationResult.set(validateModuleName(state.fullModuleName))
    }

    val levels = LEVELS.map { level -> convertLevelName(level) }
    val domains = DOMAINS.map { domain -> convertDomainName(domain) }
}