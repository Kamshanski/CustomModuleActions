package dev.itssho.module.feature.qpay.module.name.presentation.validator

import dev.itssho.module.common.component.chain.Chain
import dev.itssho.module.common.component.chain.Separator.Minus
import dev.itssho.module.common.component.chain.any
import dev.itssho.module.common.component.chain.size
import dev.itssho.module.common.component.chain.splitToChain
import dev.itssho.module.feature.qpay.module.name.presentation.converter.parseModuleName
import dev.itssho.module.feature.qpay.module.name.presentation.model.ModuleNameModel
import dev.itssho.module.feature.qpay.module.name.presentation.model.ModuleNameValidationResult
import dev.itssho.module.feature.qpay.module.name.presentation.model.ModuleNameValidationResult.*

private val NAME_PART_PATTERN = Regex("[a-z][a-z\\d]*")

fun validateModuleName(fullName: String): ModuleNameValidationResult {
    val chain = fullName.splitToChain(Minus)
    val model: ModuleNameModel = parseModuleName(chain)

    checkErrors(fullName, chain, model)?.also { return it }
    checkWarnings(model)?.also { return it }

    return Valid()
}

private fun checkErrors(fullModuleName: String, moduleChain: Chain<Minus>, model: ModuleNameModel): Error? {
    if (moduleChain.isEmpty()) {
        return Error.Empty()
    }

    if (fullModuleName.hasEmptyNodes() || (model.hasNoLevel() && model.hasNoDomain() && model.hasNoName())) {
        return Error.EmptyPart()
    }

    if (moduleChain.hasIllegalChar()) {
        return Error.IllegalCharError()
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

fun Chain<Minus>.hasIllegalChar(): Boolean = any { node -> !node.matches(NAME_PART_PATTERN) }

fun ModuleNameModel.hasNoName(): Boolean = namePart.isBlank()

fun ModuleNameModel.hasNoLevel(): Boolean = level == null

fun ModuleNameModel.hasNoDomain(): Boolean = domain == null

fun String.hasEmptyNodes(): Boolean = startsWith("-") || endsWith("-") || contains("--")

fun Chain<Minus>.isEmpty(): Boolean = size == 0