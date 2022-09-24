package dev.itssho.module.common.component.fso.entity

import dev.itssho.module.common.component.chain.Chain
import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.common.component.chain.castTo
import dev.itssho.module.util.OS

class Directory(path: Chain<out Separator>): Fso {

    override val pathChain: Chain<OS> = path.castTo(OS)

    override fun toString(): String {
        return "Directory(pathChain=$pathChain)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Directory) return false

        if (pathChain != other.pathChain) return false

        return true
    }

    override fun hashCode(): Int {
        return pathChain.hashCode()
    }
}