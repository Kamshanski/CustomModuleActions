package count

class Count(startValue: Int, private val increment: Int) {

	val value = startValue

	fun next(): Count = Count(value + increment, increment)
}