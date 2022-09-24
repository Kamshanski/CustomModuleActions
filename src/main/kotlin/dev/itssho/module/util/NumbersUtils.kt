package dev.itssho.module.util

fun Int.nonNegative(): Int = if (this < 0) -this else this