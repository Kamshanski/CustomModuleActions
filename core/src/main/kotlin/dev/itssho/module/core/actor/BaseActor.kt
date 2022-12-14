package dev.itssho.module.core.actor

@Suppress("ComponentNotRegistered")
abstract class BaseActor(val context: Context) {

//    val scope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Swing)
//    val wrappedScope = ScopeWrapper(scope)
//
//    fun <T> varOf(value: T): Var<T> = Var(value, scope)
//
//    fun launch(block: suspend CoroutineScope.() -> Unit) {
//       scope.launch(block = block)
//    }
//
//    fun <T> Observable<T>.observe(observer: Observer<T>) {
//        observe(wrappedScope, observer)
//    }

    abstract suspend fun runAction()
}