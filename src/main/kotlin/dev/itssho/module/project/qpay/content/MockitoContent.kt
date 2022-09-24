package dev.itssho.module.project.qpay.content

import dev.itssho.module.common.component.chain.chainOf
import dev.itssho.module.project.qpay.QpayModuleTree
import dev.itssho.module.util.OS

fun QpayModuleTree.mockitoFile() {
    testRes.file("org.mockito.plugins.MockMaker", subdir = OS.chainOf("mockito-extensions")) {
        """mock-maker-inline"""
    }
}