package dev.itssho.module.util

import com.intellij.openapi.util.SystemInfo
import dev.itssho.module.common.component.chain.Separator

object OS : Separator(
    if (SystemInfo.isWindows) {
        Windows.value
    } else {
        Linux.value
    }
)