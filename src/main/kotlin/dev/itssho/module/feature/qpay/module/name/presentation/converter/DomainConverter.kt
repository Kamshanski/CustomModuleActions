package dev.itssho.module.feature.qpay.module.name.presentation.converter

import dev.itssho.module.feature.qpay.module.domain.entity.path.ModuleDomain
import dev.itssho.module.feature.qpay.module.domain.entity.path.ModuleDomain.*

private val parseMap: Map<String, ModuleDomain> = ModuleDomain.values.associateBy(::convertDomainName)

fun convertDomainName(domain: ModuleDomain): String = when (domain) {
    EWALLET -> "ewallet"
    LOANS   -> "loans"
    TRANSFER -> "transfer"
}

fun parseDomainName(domainName: String): ModuleDomain? = parseMap[domainName]