package dev.itssho.module.qpay.module.selection.domain.entity

import dev.itssho.module.hierarchy.importing.ModuleAction
import java.time.LocalDateTime

sealed interface Script {
	val path: String

	class Loading(override val path: String) : Script

	class Loaded(override val path: String, val moduleAction: ModuleAction, val timestamp: LocalDateTime) : Script

	class Failure(override val path: String, val exception: Throwable, val timestamp: LocalDateTime) : Script
}