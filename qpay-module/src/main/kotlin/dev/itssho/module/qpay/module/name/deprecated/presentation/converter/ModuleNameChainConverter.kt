package dev.itssho.module.qpay.module.name.deprecated.presentation.converter

import dev.itssho.module.component.chain.Chain
import dev.itssho.module.component.chain.Separator
import dev.itssho.module.component.chain.any.AnyChain
import dev.itssho.module.component.chain.any.EMPTY
import dev.itssho.module.component.chain.castTo
import dev.itssho.module.component.chain.getOrNull
import dev.itssho.module.component.chain.size
import dev.itssho.module.component.chain.splitToChain
import dev.itssho.module.component.chain.subchain
import dev.itssho.module.qpay.module.name.deprecated.presentation.model.ModuleNameModel

fun convertModuleName(model: ModuleNameModel): Chain<Separator.Minus> {
    val level = model.level
        ?.let(::convertLevelName)
        ?.splitToChain(Separator.Minus)
        ?: Chain.EMPTY

    val domain = model.domain
        ?.let(::convertDomainName)
        ?.splitToChain(Separator.Minus)
        ?: Chain.EMPTY

    val name = model.namePart
        .splitToChain(Separator.Minus)

    return (level + domain + name).castTo(Separator.Minus)
}

fun parseModuleName(moduleNameChain: AnyChain): ModuleNameModel {
    var domainAtStart = false
    val level = moduleNameChain.getOrNull(0)?.let(::parseLevelName)
    val domain = moduleNameChain.getOrNull(1)?.let(::parseDomainName)
        ?: moduleNameChain.getOrNull(0)?.let(::parseDomainName)?.also { domainAtStart = true }

    var nameSinceIndex = when {
        level == null && domain == null -> 0
        level == null && domain != null && domainAtStart -> 1
        level == null && domain != null && !domainAtStart -> 2
        level != null && domain == null -> 1
        level != null && domain != null -> 2
        else -> throw IllegalStateException("Cannot parse module name chain: $moduleNameChain")
    }

    val name = if (moduleNameChain.size > nameSinceIndex) {
        moduleNameChain.subchain(from = nameSinceIndex)
    } else {
        Chain.EMPTY
    }.assemble()

    return ModuleNameModel(level, domain, name)
}