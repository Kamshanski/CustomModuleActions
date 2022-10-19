package coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

inline fun <T> StateFlow<T>.observe(scope: CoroutineScope, crossinline block: (T) -> Unit) {
	scope.launch {
		this@observe.collect {
			block(it)
		}
	}
}

inline fun <T> Flow<T>.collectOn(scope: CoroutineScope, crossinline collector: (T) -> Unit) {
	scope.launch {
		this@collectOn.collect { collector(it) }
	}
}

// На случай поломки билда
@Suppress("ObjectLiteralToLambda")
suspend inline fun <T> Flow<T>.gather(crossinline collector: suspend (T) -> Unit) {
	this.collect(object : FlowCollector<T> {
		override suspend fun emit(value: T) {
			collector(value)
		}
	})
}

private operator fun <T> MutableStateFlow<T>.setValue(preparationViewModel: Any, property: KProperty<*>, t: T) {
	this.value = value
}

private operator fun <T> StateFlow<T>.getValue(preparationViewModel: Any, property: KProperty<*>): T =
	this.value