package dev.itssho.module.qpay.module.name.deprecated.presentation.converter

import dev.itssho.module.qpay.module.name.deprecated.domain.entity.ModuleDomain
import dev.itssho.module.qpay.module.name.deprecated.domain.entity.ModuleDomain.EWALLET
import dev.itssho.module.qpay.module.name.deprecated.domain.entity.ModuleDomain.LOANS
import dev.itssho.module.qpay.module.name.deprecated.domain.entity.ModuleDomain.TRANSFER

private val parseMap: Map<String, ModuleDomain> = ModuleDomain.values.associateBy(::convertDomainName)

fun convertDomainName(domain: ModuleDomain): String = when (domain) {
	EWALLET  -> "ewallet"
	LOANS    -> "loans"
	TRANSFER -> "transfer"
}

fun parseDomainName(domainName: String): ModuleDomain? = parseMap[domainName]