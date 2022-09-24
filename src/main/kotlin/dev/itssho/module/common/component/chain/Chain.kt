package dev.itssho.module.common.component.chain

class Chain<S : Separator>(val nodes: List<String>, val separator: S) {

    companion object

    fun assemble(): String =
        nodes.joinToString(separator = separator.value)

    override fun toString(): String =
        assemble()

    operator fun <S2 : Separator> plus(chain: Chain<S2>): Chain<S> =
        Chain(this.nodes + chain.nodes, this.separator)

    operator fun plus(node: String): Chain<S> = Chain(this.nodes + node, this.separator)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Chain<*>) return false

        if (nodes != other.nodes) return false
        if (separator != other.separator) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nodes.hashCode()
        result = 31 * result + separator.hashCode()
        return result
    }
}