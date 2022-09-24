package delegate

import java.io.Serializable
import java.lang.ref.WeakReference

// Not Synchronized
public fun <T> weakLazy(initializer: () -> T): Lazy<T> = SynchronizedLazyImpl(initializer)

private class SynchronizedLazyImpl<out T>(private val initializer: () -> T) : Lazy<T>, Serializable {
	private var _value: WeakReference<Any?> = WeakReference(UNINITIALIZED_VALUE)

	override val value: T
		get() {
			val v1 = _value.get()
			if (v1 !== UNINITIALIZED_VALUE) {
				@Suppress("UNCHECKED_CAST")
				return v1 as T
			}
			val v2 = initializer()
			_value.clear()
			_value = WeakReference(v2)

			@Suppress("UNCHECKED_CAST")
			return v2
		}

	override fun isInitialized(): Boolean = _value.get() !== UNINITIALIZED_VALUE

	override fun toString(): String = if (isInitialized()) value.toString() else "Lazy value not initialized yet."

	private object UNINITIALIZED_VALUE
}