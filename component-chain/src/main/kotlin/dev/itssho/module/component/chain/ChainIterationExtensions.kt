package dev.itssho.module.component.chain

fun <S : Separator> Chain<S>.walk(block: (chain: Chain<S>) -> Unit) {
    for (i in 1..nodes.size) {
        val intermediateChain = Chain(nodes.subList(0, i), separator)
        block(intermediateChain)
    }
}

inline fun <S : Separator> Chain<S>.onEach(block: (node: String) -> Unit): Chain<S> = apply {
    nodes.forEach(block)
}


