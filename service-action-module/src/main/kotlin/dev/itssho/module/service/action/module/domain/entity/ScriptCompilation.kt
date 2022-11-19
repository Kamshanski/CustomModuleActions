package dev.itssho.module.service.action.module.domain.entity

import dev.itssho.module.hierarchy.importing.ModuleAction
import java.time.LocalDateTime

class ScriptCompilation(
	val hash: ByteArray,
	val timestamp: LocalDateTime,
	val moduleAction: ModuleAction,
)