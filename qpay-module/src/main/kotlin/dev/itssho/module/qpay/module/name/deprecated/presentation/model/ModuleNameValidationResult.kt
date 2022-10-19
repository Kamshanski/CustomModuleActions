package dev.itssho.module.qpay.module.name.deprecated.presentation.model

sealed interface ModuleNameValidationResult {

    class Valid : ModuleNameValidationResult

    sealed interface Warning : ModuleNameValidationResult {

        class MissingParts(val level: Boolean, val domain: Boolean, val name: Boolean) : Warning
    }

    sealed interface Failure : ModuleNameValidationResult {

        class IllegalChar: Failure

        class Empty : Failure

        class EmptyPart : Failure
    }
}