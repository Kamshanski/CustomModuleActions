package coroutine

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

fun <T, R> StateFlow<T>.mapState(map: (T) -> R): StateFlow<R> = object : StateFlow<R> {
	override val replayCache: List<R>
		get() = this@mapState.replayCache.map(map)

	override suspend fun collect(collector: FlowCollector<R>): Nothing {
		this@mapState.gather { collector.emit(map(it)) }
	}

	override val value: R
		get() = map(this@mapState.value)
}