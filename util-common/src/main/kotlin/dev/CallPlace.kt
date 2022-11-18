package dev

import chrono.SIMPLE_DATE_TIME_FORMATTER
import java.time.LocalDateTime

/**
 * Печатает класс и метод вызова этой функции.
 * Также печатает переданные [args]. Iterable и Array имеют форматирование c квадратными скобками и элементами через запятую
 *
 * @sample
 * При вызовае getCallPlace(5, "99\n88", DataClass(5, "99\n88"), listOf(5.5, 89, null))
 * <pre>
 *     {app} sandbox.Sandbox pipa$popa() Sandbox.kt:13
 *         with args: [
 *             5,
 *             99
 *             88,
 *             DataClass(int=5, string=99
 *             88
 *             ),
 *             [5.5, 89, null]
 *         ].
 * </pre>
 **/
fun getCallPlace(vararg args: Any?, showFullClassName: Boolean = false, showThread: Boolean = true, showClassLoader: Boolean = false, startFromMethodName: String = "getCallPlace"): String {
	val stackTrace = Throwable().stackTrace
	val startIndex = stackTrace.indexOfLast { it.methodName.contains(startFromMethodName) } + 1
	val ste: StackTraceElement = stackTrace[startIndex]

	val threadBlock = if (showThread) "@${Thread.currentThread().name} " else ""
	val classLoaderBlock = if (showClassLoader) "{${ste.classLoaderName}} " else ""
	val className = if (showFullClassName) ste.className else shortClassName(ste.className)

	val callPlaceString =
		"$threadBlock$classLoaderBlock$className ${ste.methodName}() ${ste.fileName}:${ste.lineNumber}"

	return if (args.size == 0) {
		callPlaceString
	} else {
		val argsStrings = args.joinToString(
			separator = ",\n\t\t",
			prefix = "\twith args: [\n\t\t",
			postfix = "\n\t].",
			transform = { argToString(it).replace("\n", "\n\t\t") },
		)
		callPlaceString + "\n" + argsStrings
	}
}

fun getTimedCallPlace(vararg args: Any?, showFullClassName: Boolean = false, showThread: Boolean = true, showClassLoader: Boolean = false, startFromMethodName: String = "getTimedCallPlace") =
	"[${SIMPLE_DATE_TIME_FORMATTER.format(LocalDateTime.now())}] " + getCallPlace(
		args = args,
		showThread = showThread,
		startFromMethodName = startFromMethodName,
		showClassLoader = showClassLoader,
		showFullClassName = showFullClassName,
	)

private fun shortClassName(fullClassName: String): String =
	fullClassName.substringAfterLast(".")

private fun argToString(any: Any?): String =
	when(any) {
		null -> "null"
		is Iterable<*> -> any.joinToString(prefix = "[", postfix = "]") { argToString(it) }
		is Array<*> -> any.joinToString(prefix = "[", postfix = "]") { argToString(it) }
		else -> any.toString()
	}