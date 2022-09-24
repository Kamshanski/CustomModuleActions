package dev.itssho.module.common.component.chain

fun <S : Separator> List<String>.splitNodes(separator: S): List<String> =
    map { it.split(separator.value) }.filter { it.isNotEmpty() }.flatten()

internal inline fun <T> T.applyIf(condition: Boolean, block: (T) -> T): T =
    if (condition) {
        block(this)
    } else {
        this
    }