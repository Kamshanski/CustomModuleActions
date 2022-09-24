package dev.itssho.module.qpay.module.name.presentation.converter

import dev.itssho.module.qpay.module.name.domain.entity.ModuleLevel
import dev.itssho.module.qpay.module.name.domain.entity.ModuleLevel.*

private val parseMap: Map<String, ModuleLevel> = ModuleLevel.values.associateBy(::convertLevelName)

fun convertLevelName(level: ModuleLevel): String = when (level) {
    FEATURE   -> "feature"
    SHARED    -> "shared"
    COMPONENT -> "component"
    DESIGN    -> "design"
    UTIL      -> "util"
}

fun parseLevelName(levelName: String): ModuleLevel? = parseMap[levelName]