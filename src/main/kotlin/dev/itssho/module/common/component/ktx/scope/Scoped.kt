package dev.itssho.module.common.component.ktx.scope

import dev.itssho.module.common.component.value.Observable
import dev.itssho.module.common.component.value.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred

interface Scoped {

    fun launch(block: suspend CoroutineScope.() -> Unit)

    fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T>

    fun <T> Observable<T>.observe(observer: Observer<T>)
}