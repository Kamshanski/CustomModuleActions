package dev.itssho.module.qpay.module.name.domain.entity

enum class ModuleDomain {
    EWALLET,
    LOANS,
    TRANSFER,
    ;

    companion object {
        val values: List<ModuleDomain> = ModuleDomain.values().toList()
    }
}