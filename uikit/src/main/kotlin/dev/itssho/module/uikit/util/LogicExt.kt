package dev.itssho.module.uikit.util

inline fun <T> Boolean.onTrue(act: () -> T): T? =
    when {
        this -> act()
        else -> null
    }
