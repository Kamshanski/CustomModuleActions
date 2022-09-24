package dev.itssho.module.common.component.value

import coroutine.gather
import dev.itssho.module.common.component.ktx.scope.ScopeWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.EmptyCoroutineContext

open class Val<T>(value: T, protected val scope: ScopeWrapper) : Read<T> {
    constructor(value: T, scope: CoroutineScope): this(value, ScopeWrapper(scope))

    protected val flow: MutableStateFlow<T> = MutableStateFlow(value)

    override fun get(): T = flow.value

    override fun observe(scope: ScopeWrapper, handleNewValue: Observer<T>) {
        scope.launch {
            flow.gather { handleNewValue(it) }
        }
    }

    companion object {

        private val DEAD_SCOPE = CoroutineScope(EmptyCoroutineContext)

        fun <T> unobservableVal(value: T): Val<T> = object : Val<T>(value, DEAD_SCOPE) {
            override fun get(): T = value

            override fun observe(scope: ScopeWrapper, handleNewValue: Observer<T>) {}
        }
    }

    fun <R> map(transform: (T) -> R): Val<R> = MappedVal(this, transform)

    class MappedVal<T, R>(private val original: Val<T>, private val transform: (T) -> R): Val<R>(transform(original.get()), original.scope) {
        override fun get(): R {
            return transform(original.get())
        }

        override fun observe(scope: ScopeWrapper, handleNewValue: Observer<R>) {
            original.observe(scope) { handleNewValue(transform(it)) }
        }
    }
}