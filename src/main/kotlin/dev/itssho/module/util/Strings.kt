package dev.itssho.module.util

fun String.containsUpperCase(): Boolean {
    for (i in 0 until length) {
        if (get(i).isUpperCase()) {
            return true
        }
    }
    return false
}

fun String.containsLowerCase(): Boolean {
    for (i in 0 until length) {
        if (get(i).isLowerCase()) {
            return true
        }
    }
    return false
}

fun String.containsSymbolsCase(): Boolean {
    for (i in 0 until length) {
        if (!get(i).isLetterOrDigit()) {
            return true
        }
    }
    return false
}