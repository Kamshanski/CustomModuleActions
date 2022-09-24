package dev.itssho.module.feature.qpay.module.name.presentation.model

import dev.itssho.module.feature.qpay.module.domain.entity.path.ModuleDomain
import dev.itssho.module.feature.qpay.module.domain.entity.path.ModuleLevel

data class ModuleNameModel(
    val level: ModuleLevel?,
    val domain: ModuleDomain?,
    val namePart: String,
)