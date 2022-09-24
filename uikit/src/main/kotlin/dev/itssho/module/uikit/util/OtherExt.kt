package dev.itssho.module.uikit.util

fun List<() -> Unit>.invokeAll() {
    forEach { it.invoke() }
}