package dev.itssho.module.common.component.chain.any

import dev.itssho.module.common.component.chain.Chain
import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.common.component.chain.chainOf
import dev.itssho.module.common.component.chain.splitToChain

typealias AnyChain = Chain<out Separator>


fun List<String>.splitToAnyChain(): AnyChain =
    splitToChain(Separator.Space)

fun String.splitToAnyChain(): AnyChain =
    splitToChain(Separator.Space)


fun anyChainOf(vararg nodes: String, splitNodes: Boolean = false): AnyChain =
    Separator.Space.chainOf(nodes = nodes, splitNodes = splitNodes)