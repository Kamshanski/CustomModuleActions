package dev.itssho.module.component.scripting

data class ScriptEvalException(override val message: String? = null, override val cause: Throwable? = null) : RuntimeException(message, cause)