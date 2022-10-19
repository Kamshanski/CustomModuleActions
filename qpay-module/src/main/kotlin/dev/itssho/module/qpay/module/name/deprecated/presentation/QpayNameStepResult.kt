package dev.itssho.module.qpay.module.name.deprecated.presentation

sealed interface QpayNameStepResult {

	object Nothing : QpayNameStepResult

	data class Name(val name: String) : QpayNameStepResult
}