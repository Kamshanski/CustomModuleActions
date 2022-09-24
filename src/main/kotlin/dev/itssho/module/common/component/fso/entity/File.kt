package dev.itssho.module.common.component.fso.entity

import dev.itssho.module.common.component.chain.Chain
import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.common.component.chain.castTo
import dev.itssho.module.common.component.chain.removeLast
import dev.itssho.module.util.OS

class File(path: Chain<out Separator>, val content: String): Fso {

    val directory = Directory(path.removeLast(1))

    override val pathChain: Chain<OS> = path.castTo(OS)

    override fun toString(): String {
        return "File(content='$content', pathChain=$pathChain)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is File) return false

        if (content != other.content) return false
        if (pathChain != other.pathChain) return false

        return true
    }

    override fun hashCode(): Int {
        var result = content.hashCode()
        result = 31 * result + pathChain.hashCode()
        return result
    }

}