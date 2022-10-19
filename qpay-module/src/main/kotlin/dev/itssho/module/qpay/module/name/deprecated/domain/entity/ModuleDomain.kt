package dev.itssho.module.qpay.module.name.deprecated.domain.entity

enum class ModuleDomain {
    EWALLET,
    LOANS,
    TRANSFER,
    ;

    companion object {
        val values: List<ModuleDomain> = ModuleDomain.values().toList()
    }
}