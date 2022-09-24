package dev.itssho.module.qpay.module.name.actor

sealed interface QpayNameStepResult {

	object Nothing : QpayNameStepResult

	data class Name(val name: List<String>) : QpayNameStepResult
}