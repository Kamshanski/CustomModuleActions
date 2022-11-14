package dev

fun <T> Iterator<T>.cached(): CachedIterator<T> = CachedIterator(this)

fun <T> generateLoopCachedSequence(vararg repeatList: T): CachedIterator<T> = generateLoopCachedSequence(repeatList.toList())

fun <T> generateLoopCachedSequence(repeatList: List<T>): CachedIterator<T> = generateSequence {
	repeatList
}
	.flatten()
	.iterator()
	.cached()