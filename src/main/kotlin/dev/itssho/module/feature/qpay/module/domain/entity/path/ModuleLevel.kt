package dev.itssho.module.feature.qpay.module.domain.entity.path

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