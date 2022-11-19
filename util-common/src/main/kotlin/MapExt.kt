inline fun <K, V, R> Map<K, V>.mapNotNullValues(transform: (Map.Entry<K, V>) -> R?): Map<K, R> {
	val map = LinkedHashMap<K, R>(this.size)
	for (item in this) {
		val value = transform(item)
		if (value != null) {
			map.put(item.key, value)
		}
	}
	return map
}