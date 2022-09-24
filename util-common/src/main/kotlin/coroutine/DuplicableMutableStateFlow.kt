package coroutine

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private class Wrap<T>(val value: T)

class DuplicableMutableStateFlow<T>(initialValue: T) : MutableStateFlow<T> {
	private val actualFlow = MutableStateFlow(Wrap(initialValue))
	override val subscriptionCount: StateFlow<Int>
		get() = actualFlow.subscriptionCount

	override suspend fun emit(value: T) {
		actualFlow.emit(Wrap(value))
	}

	@ExperimentalCoroutinesApi
	override fun resetReplayCache() {
		actualFlow.resetReplayCache()
	}

	override fun tryEmit(value: T): Boolean =
		actualFlow.tryEmit(Wrap(value))

	override var value: T
		get() = actualFlow.value.value
		set(value) {actualFlow.value = Wrap(value)}

	override fun compareAndSet(expect: T, update: T): Boolean =
		actualFlow.compareAndSet(Wrap(expect), Wrap(update))

	override val replayCache: List<T>
		get() = actualFlow.replayCache.map { it.value }

	override suspend fun collect(collector: FlowCollector<T>): Nothing {
		actualFlow.collect { value -> collector.emit(value.value) }
	}

	fun emitSame() {
		value = value
	}
}

//fun <T> DuplicableMutableStateFlow(initialValue: T): MutableStateFlow<T> = MutableStateFlow()