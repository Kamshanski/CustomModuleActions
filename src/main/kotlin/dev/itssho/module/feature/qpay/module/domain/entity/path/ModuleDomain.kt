package dev.itssho.module.feature.qpay.module.domain.entity.path

enum class ModuleDomain {
    EWALLET,
    LOANS,
    TRANSFER,
    ;

    companion object {
        val values: List<ModuleDomain> = ModuleDomain.values().toList()
    }
}