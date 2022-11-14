
inline fun <T> List<T>.contains(predicate: (T) -> Boolean): Boolean = indexOfFirst(predicate) > -1

inline fun <T> List<T>.replaceAll(deleteItem: T, newItem: T, crossinline compare: ((T, T) -> Boolean) = { thiz, other -> thiz == other }) = map { item ->
	if (compare(item, deleteItem)) {
		newItem
	} else {
		item
	}
}