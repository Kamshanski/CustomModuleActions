package dev.itssho.module.util

public inline fun <T> Iterable<T>.filterThrown(predicate: (T) -> Unit): Map<T, Throwable> = associateWith {
    try {
        predicate(it)
        null
    } catch (exception: Throwable) {
        exception
    }
}
    .filter { it.value != null }
    .map { it.key to it.value!! }
    .toMap()