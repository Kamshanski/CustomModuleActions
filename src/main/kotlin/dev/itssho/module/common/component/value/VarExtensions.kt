package dev.itssho.module.common.component.value

import java.util.concurrent.atomic.AtomicBoolean

fun <T> swap(var1: Var<T>, var2: Var<T>) {
    val tmp = var1.get()
    var1.set(var2.get())
    var2.set(tmp)
}

fun <T> Var<T>.setValueWithLock(value: T, lock: AtomicBoolean) {
    if (!lock.get()) {
        lock.set(true)

        this.set(value)

        lock.set(false)
    }
}

fun <T> Var<T>.asVal(): Val<T> = this