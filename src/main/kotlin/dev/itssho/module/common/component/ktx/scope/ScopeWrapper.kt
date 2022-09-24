package dev.itssho.module.common.component.ktx.scope

import dev.itssho.module.common.component.value.Observable
import dev.itssho.module.common.component.value.Observer
import kotlinx.coroutines.*
import kotlinx.coroutines.swing.Swing
import kotlin.coroutines.CoroutineContext

class ScopeWrapper(private val scope: CoroutineScope): Scoped {
    constructor(context: CoroutineContext): this(CoroutineScope(context))

    companion object {

        fun newIo(): ScopeWrapper = ScopeWrapper(SupervisorJob() + Dispatchers.IO)

        fun newMain(): ScopeWrapper = ScopeWrapper(SupervisorJob() + Dispatchers.Swing)
    }

    override fun launch(block: suspend CoroutineScope.() -> Unit) {
        scope.launch(block = block)
    }

    override fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> =
        scope.async(block = block)

    override fun <T> Observable<T>.observe(observer: Observer<T>) {
        observe(this@ScopeWrapper, observer)
    }
}