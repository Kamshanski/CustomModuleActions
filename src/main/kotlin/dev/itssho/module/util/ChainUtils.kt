package dev.itssho.module.util

import dev.itssho.module.common.component.chain.Chain
import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.common.component.chain.castTo
import java.nio.file.Path
import dev.itssho.module.util.OS as OSSeparator

fun getSystemSeparator(): Separator = OSSeparator

val Separator.OS: OSSeparator get() = OSSeparator

fun Chain<*>.toPath(): Path =
    this.castTo(OSSeparator).toString().let(Path::of)