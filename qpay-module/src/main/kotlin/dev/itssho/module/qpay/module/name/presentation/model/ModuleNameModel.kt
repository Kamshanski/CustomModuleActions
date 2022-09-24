package dev.itssho.module.qpay.module.name.presentation.model

import dev.itssho.module.qpay.module.name.domain.entity.ModuleDomain
import dev.itssho.module.qpay.module.name.domain.entity.ModuleLevel

data class ModuleNameModel(
    val level: ModuleLevel?,
    val domain: ModuleDomain?,
    val namePart: String,
)