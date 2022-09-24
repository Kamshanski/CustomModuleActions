package dev.itssho.module.component.chain

inline fun <S: Separator> Chain<S>.any(predicate: (node: String) -> Boolean): Boolean =
    nodes.any(predicate)

inline fun <S: Separator> Chain<S>.none(predicate: (node: String) -> Boolean): Boolean =
    nodes.none(predicate)