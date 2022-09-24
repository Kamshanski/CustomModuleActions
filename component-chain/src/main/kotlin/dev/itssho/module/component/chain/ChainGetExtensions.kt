package dev.itssho.module.component.chain

import dev.itssho.module.component.chain.any.AnyChain

fun AnyChain.getOrNull(index: Int): String? = nodes.getOrNull(index)

operator fun AnyChain.get(index: Int): String = nodes[index]