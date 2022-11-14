import java.io.PrintWriter
import java.io.StringWriter

fun Throwable.fullStackTraceString(): String {
	val writer = StringWriter()
	val printWriter = PrintWriter(writer)
	printStackTrace(printWriter)
	return writer.toString()
}