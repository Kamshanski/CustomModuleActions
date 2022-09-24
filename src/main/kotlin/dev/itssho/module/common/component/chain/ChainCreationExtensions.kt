package dev.itssho.module.common.component.chain

fun <S : Separator> List<String>.splitToChain(separator: S): Chain<S> =
    Chain(this.splitNodes(separator), separator)

fun <S : Separator> String.splitToChain(separator: S): Chain<S> =
    Chain(listOf(this).splitNodes(separator), separator)


fun <S : Separator> S.chainOf(vararg nodes: String, splitNodes: Boolean = false): Chain<S> {
    val nodeList = nodes.toList()
        .applyIf(splitNodes) { list -> list.splitNodes(separator = this) }

    return Chain(nodeList, this)
}