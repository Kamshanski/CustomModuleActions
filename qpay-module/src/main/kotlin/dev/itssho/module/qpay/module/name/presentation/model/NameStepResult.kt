package dev.itssho.module.qpay.module.name.presentation.model

sealed interface NameStepResult {

	object Nothing : NameStepResult

	data class Name(val name: String) : NameStepResult
}