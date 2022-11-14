package reflection

inline fun <reified T : Any> Any?.castOrNull(): T? = this as? T

inline fun <reified T : Any> Any?.castOrThrow(error: (Any?) -> String): T = this as? T ?: throw ClassCastException(error(this))

inline fun <reified T : Any> T?.castNotNull(error: () -> String): T = this ?: throw IllegalArgumentException(error())