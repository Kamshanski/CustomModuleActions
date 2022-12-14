package dev.itssho.module.qpay.module.name.domain.entity

enum class ModuleLevel {
    FEATURE,
    SHARED,
    COMPONENT,
    DESIGN,
    UTIL,
    ;

    companion object {
        val values: List<ModuleLevel> = ModuleLevel.values().toList()
    }
}