package dev.itssho.module.qpay.module.name.presentation.validator

import dev.itssho.module.component.chain.*
import dev.itssho.module.qpay.module.name.presentation.converter.parseModuleName
import dev.itssho.module.qpay.module.name.presentation.model.ModuleNameModel
import dev.itssho.module.qpay.module.name.presentation.model.ModuleNameValidationResult
import dev.itssho.module.qpay.module.name.presentation.model.ModuleNameValidationResult.*

private val NAME_PART_PATTERN = Regex("[a-z][a-z\\d]*")

fun validateModuleName(fullName: String): ModuleNameValidationResult {
    val chain = fullName.splitToChain(Separator.Minus)
    val model: ModuleNameModel = parseModuleName(chain)

    checkErrors(fullName, chain, model)?.also { return it }
    checkWarnings(model)?.also { return it }

    return Valid()
}

private fun checkErrors(fullModuleName: String, moduleChain: Chain<Separator.Minus>, model: ModuleNameModel): Failure? {
    if (moduleChain.isEmpty()) {
        return Failure.Empty()
    }

    if (fullModuleName.hasEmptyNodes() || (model.hasNoLevel() && model.hasNoDomain() && model.hasNoName())) {
        return Failure.EmptyPart()
    }

    if (moduleChain.hasIllegalChar()) {
        return Failure.IllegalChar()
    }

    return null
}

private fun checkWarnings(model: ModuleNameModel): Warning? {
    if (model.hasNoLevel() || model.hasNoDomain() || model.hasNoName()) {
        return Warning.MissingParts(
            level = model.hasNoLevel(),
            domain = model.hasNoDomain(),
            name = model.hasNoName(),
        )
    }

    return null
}

fun Chain<Separator.Minus>.hasIllegalChar(): Boolean = any { node -> !node.matches(NAME_PART_PATTERN) }

fun ModuleNameModel.hasNoName(): Boolean = namePart.isBlank()

fun ModuleNameModel.hasNoLevel(): Boolean = level == null

fun ModuleNameModel.hasNoDomain(): Boolean = domain == null

fun String.hasEmptyNodes(): Boolean = startsWith("-") || endsWith("-") || contains("--")

fun Chain<Separator.Minus>.isEmpty(): Boolean = size == 0