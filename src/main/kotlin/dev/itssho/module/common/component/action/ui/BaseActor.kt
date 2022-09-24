package dev.itssho.module.common.component.action.ui

import dev.itssho.module.common.component.ktx.scope.ScopeWrapper
import dev.itssho.module.common.component.value.Observable
import dev.itssho.module.common.component.value.Observer
import dev.itssho.module.common.component.value.Var
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.swing.Swing

@Deprecated("", replaceWith = ReplaceWith("BaseActor", "dev.itssho.module.core.actor.BaseActor"))
abstract class BaseActor(val context: Context) {

	val scope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Swing)
	val wrappedScope = ScopeWrapper(scope)

	fun <T> varOf(value: T): Var<T> = Var(value, scope)

	fun launch(block: suspend CoroutineScope.() -> Unit) {
		scope.launch(block = block)
	}

	fun <T> Observable<T>.observe(observer: Observer<T>) {
		observe(wrappedScope, observer)
	}

	abstract fun runAction()
}