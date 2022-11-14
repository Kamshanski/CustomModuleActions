package dev

class CachedIterator<T>(val iterator: Iterator<T>) : Iterator<T> {

	private object NULL

	private var last: Any? = NULL

	override fun hasNext(): Boolean =
		iterator.hasNext()

	override fun next(): T {
		val newLast = iterator.next()
		last = newLast
		return newLast
	}

	fun last(): T = when (last) {
		NULL -> throw IllegalStateException("Iterator contains no cache")
		else -> last as T
	}
}