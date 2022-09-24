package dev.itssho.module.component.chain.any

import dev.itssho.module.component.chain.Chain
import dev.itssho.module.component.chain.Separator
import dev.itssho.module.component.chain.chainOf
import dev.itssho.module.component.chain.splitToChain

typealias AnyChain = Chain<out Separator>


fun List<String>.splitToSpaceChain(): AnyChain =
    splitToChain(Separator.Space)

fun String.splitToSpaceChain(): AnyChain =
    splitToChain(Separator.Space)


fun anyChainOf(vararg nodes: String, splitNodes: Boolean = false): AnyChain =
    Separator.Space.chainOf(nodes = nodes, splitNodes = splitNodes)