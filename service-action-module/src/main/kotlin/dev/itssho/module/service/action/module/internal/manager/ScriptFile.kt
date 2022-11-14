package dev.itssho.module.service.action.module.internal.manager

import java.nio.file.Path

internal class ScriptFile(
	val path: Path,
	val content: String,
	val hash: ByteArray,
)