package dev.itssho.module.qpay.module.name.deprecated.presentation.converter

import dev.itssho.module.qpay.module.name.deprecated.domain.entity.ModuleLevel
import dev.itssho.module.qpay.module.name.deprecated.domain.entity.ModuleLevel.COMPONENT
import dev.itssho.module.qpay.module.name.deprecated.domain.entity.ModuleLevel.DESIGN
import dev.itssho.module.qpay.module.name.deprecated.domain.entity.ModuleLevel.FEATURE
import dev.itssho.module.qpay.module.name.deprecated.domain.entity.ModuleLevel.SHARED
import dev.itssho.module.qpay.module.name.deprecated.domain.entity.ModuleLevel.UTIL

private val parseMap: Map<String, ModuleLevel> = ModuleLevel.values.associateBy(::convertLevelName)

fun convertLevelName(level: ModuleLevel): String = when (level) {
    FEATURE   -> "feature"
    SHARED    -> "shared"
    COMPONENT -> "component"
    DESIGN    -> "design"
    UTIL      -> "util"
}

fun parseLevelName(levelName: String): ModuleLevel? = parseMap[levelName]