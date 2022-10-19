package string

/** Escape for HTML
 * @author [android.text.Html]
 */
fun String.escapeHtml(): String {
	val out = StringBuilder()
	escapeHtmlTo(out)
	return out.toString()
}

/** Escape for HTML
 * @author [android.text.Html]
 */
fun String.escapeHtmlTo(out: StringBuilder) {
	var i = 0
	val end = this.length

	while (i < end) {
		val c = this[i]
		if (c == '<') {
			out.append("&lt;")
		} else if (c == '>') {
			out.append("&gt;")
		} else if (c == '&') {
			out.append("&amp;")
		} else if (c in '\ud800'..'\udfff') {
			if (c < '\udc00' && i + 1 < end) {
				val d = this[i + 1]
				if (d in '\udc00'..'\udfff') {
					++i
					val codepoint = 65536 or (c - '\ud800' shl 10) or d - '\udc00'
					out.append("&#").append(codepoint).append(";")
				}
			}
		} else if (c in ' '..'~') {
			if (c != ' ') {
				out.append(c)
			} else {
				while (i + 1 < end && this[i + 1] == ' ') {
					out.append("&nbsp;")
					++i
				}
				out.append(' ')
			}
		} else {
			out.append("&#").append(c).append(";")
		}
		++i
	}
}