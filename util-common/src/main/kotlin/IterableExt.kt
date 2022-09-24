
fun <T> List<T>.contentEquals(other: Set<T>): Boolean {
	if (this.size != other.size) {
		return false
	}

	if (size == 0) {
		return true
	}

	val thisIter = this.iterator()
	val otherIter = this.iterator()

	while (thisIter.hasNext()) {
		val thisItem = thisIter.next()
		val otherItem = otherIter.next()

		if (thisItem != otherItem) {
			return false
		}
	}

	return true
}