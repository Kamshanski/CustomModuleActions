package dev.itssho.module.component.chain.any

import dev.itssho.module.component.chain.Chain
import dev.itssho.module.component.chain.Separator
import dev.itssho.module.component.chain.castTo

private val EMPTY_INSTANCE = Chain(emptyList(), Separator.Space)

val Chain.Companion.EMPTY: AnyChain get() = EMPTY_INSTANCE

fun emptyChain(): AnyChain = EMPTY_INSTANCE

fun <S : Separator> emptyChain(separator: S): AnyChain = emptyChain().castTo(separator)