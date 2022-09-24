package dev.itssho.module.common.component.moduletree

import dev.itssho.module.common.component.chain.splitToChain
import dev.itssho.module.util.OS

fun ModuleTree.dataFolders() {
    mainModule.directory("datasource", "data".splitToChain(OS))
    mainModule.directory("converter", "data".splitToChain(OS))
    mainModule.directory("model", "data".splitToChain(OS))
    mainModule.directory("repository", "data".splitToChain(OS))
}

fun ModuleTree.domainFolders() {
    mainModule.directory("usecase", "domain".splitToChain(OS))
    mainModule.directory("scenario", "domain".splitToChain(OS))
    mainModule.directory("entity", "domain".splitToChain(OS))
    mainModule.directory("repository", "domain".splitToChain(OS))
}

fun ModuleTree.presentationFolders() {
    mainModule.directory("model", "presentation".splitToChain(OS))
    mainModule.directory("converter", "presentation".splitToChain(OS))
}

fun ModuleTree.uiFolders() {
    mainModule.directory("ui")
}

fun ModuleTree.testDataFolders() {
    testModule.directory("datasource", "data".splitToChain(OS))
    testModule.directory("converter", "data".splitToChain(OS))
    testModule.directory("repository", "data".splitToChain(OS))
}

fun ModuleTree.testDomainFolders() {
    testModule.directory("usecase", "domain".splitToChain(OS))
    testModule.directory("scenario", "domain".splitToChain(OS))
}

fun ModuleTree.testPresentationFolders() {
    testModule.directory("converter", "presentation".splitToChain(OS))
}