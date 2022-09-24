package dev.itssho.module.util

inline fun <T> Boolean.onTrue(act: () -> T): T? =
    when {
        this -> act()
        else -> null
    }
