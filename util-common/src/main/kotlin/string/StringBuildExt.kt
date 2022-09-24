package string

public operator fun String.times(length: Int): String = repeat(length)

public fun String.fit(length: Int, padChar: Char) = if (this.length > length) {
		this.substring(0, length)
	} else {
		this.padEnd(length, padChar)
	}

public fun String.filter(char: Char) = filter { it != char }
public fun String.filter(vararg chars: Char) = filter { it !in chars }