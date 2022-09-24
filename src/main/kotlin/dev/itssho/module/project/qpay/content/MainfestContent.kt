package dev.itssho.module.project.qpay.content

import dev.itssho.module.common.component.chain.Separator
import dev.itssho.module.common.component.chain.castTo
import dev.itssho.module.project.qpay.QpayModuleTree

fun QpayModuleTree.manifestFile() {
    main.file("AndroidManifest.xml") { context ->
        """<manifest package="${(context.companyChain + context.moduleChain).castTo(Separator.Package)}" />"""
    }
}