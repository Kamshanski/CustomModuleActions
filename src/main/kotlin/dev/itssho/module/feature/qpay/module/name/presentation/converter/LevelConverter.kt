package dev.itssho.module.feature.qpay.module.name.presentation.converter

import dev.itssho.module.feature.qpay.module.domain.entity.path.ModuleLevel
import dev.itssho.module.feature.qpay.module.domain.entity.path.ModuleLevel.*

private val parseMap: Map<String, ModuleLevel> = ModuleLevel.values.associateBy(::convertLevelName)

fun convertLevelName(level: ModuleLevel): String = when (level) {
    FEATURE   -> "feature"
    SHARED    -> "shared"
    COMPONENT -> "component"
    DESIGN    -> "design"
    UTIL      -> "util"
}

fun parseLevelName(levelName: String): ModuleLevel? = parseMap[levelName]