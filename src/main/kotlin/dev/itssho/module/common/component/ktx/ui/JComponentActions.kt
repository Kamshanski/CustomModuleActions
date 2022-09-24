package dev.itssho.module.common.component.ktx.ui

import dev.itssho.module.common.component.value.Observable
import dev.itssho.module.common.component.value.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import javax.swing.JComponent

fun JComponent.launch(block: suspend CoroutineScope.() -> Unit) {
    scope.launch(block = block)
}

fun <T> JComponent.async(block: suspend CoroutineScope.() -> T): Deferred<T> =
    scope.async(block = block)

suspend fun <T> JComponent.observe(variable: Observable<T>, observer: Observer<T>) {
    variable.observe(scope, observer)
}