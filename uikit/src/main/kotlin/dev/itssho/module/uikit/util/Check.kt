package dev.itssho.module.uikit.util

fun <T> checkIncludes(value: T, list: Iterable<T>) {
    check(value in list) {
        "$value was not found in list: ${list.joinToString()}"
    }
}

inline fun requireNonNegative(value: Int, lazyMessage: () -> Any): Int {
    if (value < 0) {
        val message = lazyMessage()
        throw IllegalStateException(message.toString())
    }

    return value
}

fun requireNonNegative(value: Int): Int = requireNonNegative(value) { "$value is negative." }