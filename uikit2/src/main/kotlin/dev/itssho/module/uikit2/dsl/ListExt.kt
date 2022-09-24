package dev.itssho.module.uikit2.dsl

fun <T> List<T>.subListFrom(fromIndex: Int, takePredicate: (T) -> Boolean): List<T> {
	if (fromIndex !in indices) {
		throw IndexOutOfBoundsException(fromIndex)
	}

	val list = ArrayList<T>(this.size - fromIndex)

	var index = fromIndex
	while (index < size) {
		val item = get(index)

		if (!takePredicate(item)) {
			break
		}

		list.add(item)

		index++
	}

	return list
}