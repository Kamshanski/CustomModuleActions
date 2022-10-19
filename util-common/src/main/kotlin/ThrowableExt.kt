import java.io.PrintWriter
import java.io.StringWriter

fun Throwable.fullStackTraceString(): String {
	val writer = StringWriter()
	this.printStackTrace(PrintWriter(writer))
	return writer.toString()
}