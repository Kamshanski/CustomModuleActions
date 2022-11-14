package dev.itssho.module.service.action.module.internal

internal fun <T> List<T>.removeFirst(compare: (T) -> Boolean): List<T> {
	val newList = ArrayList<T>(this.size)
	var found = false
	for (item in this) {
		if (!found) {
			found = compare(item)
			if (found) {
				continue
			}
		}
		newList.add(item)
	}
	return newList
}

internal fun <T> List<T>.replaceFirst(newItem: T, compare: (T) -> Boolean): List<T> {
	val newList = ArrayList<T>(this.size)
	var found = false
	for (item in this) {
		if (!found) {
			found = compare(item)
			if (found) {
				newList.add(newItem)
				continue
			}
		}
		newList.add(item)
	}
	return newList
}

