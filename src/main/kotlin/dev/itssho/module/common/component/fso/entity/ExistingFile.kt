package dev.itssho.module.common.component.fso.entity

import dev.itssho.module.common.component.chain.Chain
import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.common.component.chain.castTo
import dev.itssho.module.util.OS

class ExistingFile<T>(
    path: Chain<out Separator>,
    private val insertContent: T,
    private val format: (fileContent: String, insertContent: T) -> String
) : Fso {

    // project relative path to file
    override val pathChain: Chain<OS> = path.castTo(OS)

    fun formatContent(fileContent: String): String =
        format(fileContent, insertContent)

    override fun toString(): String {
        return "ExistingFile(insertContent=$insertContent, pathChain=$pathChain)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ExistingFile<*>) return false

        if (insertContent != other.insertContent) return false
        if (format != other.format) return false
        if (pathChain != other.pathChain) return false

        return true
    }

    override fun hashCode(): Int {
        var result = insertContent?.hashCode() ?: 0
        result = 31 * result + format.hashCode()
        result = 31 * result + pathChain.hashCode()
        return result
    }
}