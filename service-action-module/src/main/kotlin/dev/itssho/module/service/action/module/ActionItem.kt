package dev.itssho.module.service.action.module

import java.nio.file.Path
import java.time.LocalDateTime

sealed interface ActionItem {
	val path: Path

	class Loading(override val path: Path, val cache: ScriptCompilation? = null) : ActionItem

	class Loaded(override val path: Path, val compilation: ScriptCompilation) : ActionItem

	class Failure(override val path: Path, val exception: Throwable, val timestamp: LocalDateTime) : ActionItem
}