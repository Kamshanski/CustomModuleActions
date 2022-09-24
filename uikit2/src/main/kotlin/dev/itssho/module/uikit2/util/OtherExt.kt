package dev.itssho.module.uikit2.util

fun List<() -> Unit>.invokeAll() {
    forEach { it.invoke() }
}