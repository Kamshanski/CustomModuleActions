package dev.itssho.module.qpay.module.name.deprecated.presentation.model

import dev.itssho.module.qpay.module.name.deprecated.domain.entity.ModuleDomain
import dev.itssho.module.qpay.module.name.deprecated.domain.entity.ModuleLevel

data class ModuleNameModel(
    val level: ModuleLevel?,
    val domain: ModuleDomain?,
    val namePart: String,
)