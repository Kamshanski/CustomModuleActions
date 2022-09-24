package dev.itssho.module.component.chain

@Suppress("UNCHECKED_CAST")
fun <S1 : Separator, S2 : Separator> Chain<S1>.castTo(separator: S2): Chain<S2> =
    when (this.separator) {
        separator -> this as Chain<S2>
        else      -> Chain(this.nodes, separator)
    }

inline fun <S : Separator> Chain<S>.filter(predicate: (node: String) -> Boolean): Chain<S> =
    nodes.filter(predicate).splitToChain(separator)

inline fun <S : Separator> Chain<S>.map(transform: (node: String) -> String): Chain<S> {
    val newNodes = nodes.map(transform)
    return newNodes.splitToChain(separator)
}

fun <S : Separator> Chain<S>.removeLast(count: Int): Chain<S> =
    Chain(nodes.subList(0, nodes.size - count), separator)

fun <S : Separator> Chain<S>.subchain(from: Int = 0, to: Int = size): Chain<S> =
    Chain(nodes.subList(from, to), separator)