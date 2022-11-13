package reflection

inline fun <reified T : Any> Any?.castOrNull(): T? = this as? T