package dev.itssho.module.qpay.module.selection.presentation

import dev.itssho.module.hierarchy.importing.ModuleAction

sealed interface SelectionStepResult {

	object Nothing : SelectionStepResult

	class Compilation(val moduleAction: ModuleAction) : SelectionStepResult
}