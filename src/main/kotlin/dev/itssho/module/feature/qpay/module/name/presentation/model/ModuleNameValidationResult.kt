package dev.itssho.module.feature.qpay.module.name.presentation.model

sealed interface ModuleNameValidationResult {

    class Valid : ModuleNameValidationResult

    sealed interface Warning : ModuleNameValidationResult {

        class MissingParts(val level: Boolean, val domain: Boolean, val name: Boolean) : Warning
    }

    sealed interface Error : ModuleNameValidationResult {

        class IllegalCharError(): Error

        class Empty : Error

        class EmptyPart : Error
    }
}