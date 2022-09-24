package dev.itssho.module.common.component.value

import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.EmptyCoroutineContext

private val DEAD_SCOPE = CoroutineScope(EmptyCoroutineContext)

fun <T> unobservableVar(value: T): Var<T> = object : Var<T>(value, DEAD_SCOPE) {
    var _value = AtomicReference(value)

    override fun get(): T {
        return _value.get()
    }

    override fun set(value: T) {
        _value.set(value)
    }
}