package de.swirtz.ktsrunner.objectloader

data class ScriptEvalException(override val message: String? = null, override val cause: Throwable? = null) : RuntimeException(message, cause)