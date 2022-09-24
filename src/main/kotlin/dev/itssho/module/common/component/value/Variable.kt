package dev.itssho.module.common.component.value

import dev.itssho.module.common.component.ktx.scope.ScopeWrapper

typealias Observer<T> = suspend (T) -> Unit

interface Observable<T> {

    fun observe(scope: ScopeWrapper, handleNewValue: Observer<T>)
}

interface Read<T> : Observable<T> {

    fun get(): T
}

interface Write<T> : Observable<T> {

    fun set(value: T)
}


//
//class ObservableValue<T>(private var value: T?): Value<T>() {
//
//    private val observers: MutableSet<WeakReference<Observer<T>>> = mutableSetOf()
//
//    override fun get(): T? = value
//
//    override fun set(value: T?) {
//        this.value = value
//
//        val deleteObservers = mutableSetOf<WeakReference<Observer<T>>>()
//
//        for (reference in observers) {
//            if (this.value != value) {
//                break
//            }
//
//            val observer = reference.get()
//            if (observer != null) {
//                observer(value)
//            } else {
//                deleteObservers.add(reference)
//            }
//        }
//
//        observers.removeAll(deleteObservers)
//    }
//
//    override suspend fun observe(handleNewValue: Observer<T>) {
//        observers.add(WeakReference(handleNewValue))
//        handleNewValue(value)
//    }
//}