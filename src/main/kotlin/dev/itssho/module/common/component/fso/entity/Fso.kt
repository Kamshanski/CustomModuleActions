package dev.itssho.module.common.component.fso.entity

import dev.itssho.module.common.component.chain.Chain
import dev.itssho.module.util.OS

// File System Object
interface Fso {
    val pathChain: Chain<OS>
}