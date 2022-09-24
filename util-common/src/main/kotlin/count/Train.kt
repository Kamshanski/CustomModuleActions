package count

class Train<T> private constructor(private val iterator: Iterator<T>) {

	constructor(items: List<T>) : this(items.iterator())

	fun hasNext(): Boolean = iterator.hasNext()

	val value: T = iterator.next()

	fun next(): Train<T> = Train(iterator)

}