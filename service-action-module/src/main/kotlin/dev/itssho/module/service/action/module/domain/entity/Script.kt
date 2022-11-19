package dev.itssho.module.service.action.module.domain.entity

import dev.itssho.module.hierarchy.importing.ModuleAction
import java.time.LocalDateTime

sealed interface Script {
	val path: String

	class Loading(override val path: String, internal val cache: ScriptCompilation? = null) : Script

	class Loaded(override val path: String, internal val compilation: ScriptCompilation) : Script {

		val timestamp: LocalDateTime = compilation.timestamp

		var isUsed: Boolean = false
			private set

		fun useModuleAction(): ModuleAction {
			if (isUsed) {
				throw IllegalStateException("Dirty script access")
			}
			isUsed = true
			return compilation.moduleAction
		}
	}

	class Failure(override val path: String, val exception: Throwable, val timestamp: LocalDateTime) : Script
}