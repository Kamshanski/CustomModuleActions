package dev.itssho.module.qpay.module.name.presentation.converter

import dev.itssho.module.qpay.module.name.domain.entity.ModuleDomain
import dev.itssho.module.qpay.module.name.domain.entity.ModuleDomain.*

private val parseMap: Map<String, ModuleDomain> = ModuleDomain.values.associateBy(::convertDomainName)

fun convertDomainName(domain: ModuleDomain): String = when (domain) {
	EWALLET  -> "ewallet"
	LOANS    -> "loans"
	TRANSFER -> "transfer"
}

fun parseDomainName(domainName: String): ModuleDomain? = parseMap[domainName]