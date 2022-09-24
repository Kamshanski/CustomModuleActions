package string

public fun String.truncate(maxLen: Int, truncation: String = "..."): String {
	if (this.length <= maxLen - truncation.length) {
		return this
	}

	return StringBuilder().apply {
		for (i in 0 until (maxLen - truncation.length)) {
			append(this[i])
		}

		append(truncation)
	}.toString()
}