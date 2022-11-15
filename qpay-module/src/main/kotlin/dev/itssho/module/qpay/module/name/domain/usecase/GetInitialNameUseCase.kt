package dev.itssho.module.qpay.module.name.domain.usecase

import dev.itssho.module.hierarchy.importing.ModuleAction

class GetInitialNameUseCase(private val moduleAction: ModuleAction) {

	operator fun invoke(): String =
		moduleAction.nameHandler.getInitialName()
}