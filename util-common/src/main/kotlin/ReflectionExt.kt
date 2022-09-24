import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.superclasses

inline fun <reified T : Any> T.kotlinToString(
	omitNulls: Boolean = false,
	noinline superToString: (() -> String)? = null,
): String {

	val clazz = this::class
	val properties = mutableSetOf<KProperty1<out Any, *>>().apply {
		addAll(clazz.declaredMemberProperties)
		clazz.superclasses.map { it.declaredMemberProperties }.flatten().also { addAll(it) }
	}

	val builder = StringBuilder(32).append(T::class.simpleName).append("(")
	var nextSeparator = ""

	properties.forEach {
		val property = it.name
		val value = it.call(this)
		if (!omitNulls || value != null) {
			with(builder) {
				append(nextSeparator)
				nextSeparator = ", "
				append(property)
				append("=")
				if (value is Array<*>) {
					val arrayString = arrayOf(value).contentDeepToString()
					append(arrayString, 1, arrayString.length - 1)
				} else {
					append(value)
				}
			}
		}
	}

	if (superToString != null) {
		with(builder) {
			append(nextSeparator)
			append("super=")
			append(superToString())
		}
	}

	return builder.append(")").toString()
}