package dev.itssho.module.qpay.module.preparation.presentation

sealed interface PreparationStepResult {

	object Failure : PreparationStepResult

	object Success : PreparationStepResult
}