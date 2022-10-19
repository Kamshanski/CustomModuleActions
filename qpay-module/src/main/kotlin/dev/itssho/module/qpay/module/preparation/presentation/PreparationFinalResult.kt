package dev.itssho.module.qpay.module.preparation.presentation

data class PreparationFinalResult(
	val stepResult: PreparationStepResult,
	val exitStepNow: Boolean,
)