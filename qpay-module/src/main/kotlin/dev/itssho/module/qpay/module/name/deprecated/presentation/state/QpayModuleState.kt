package dev.itssho.module.qpay.module.name.deprecated.presentation.state

data class ModuleNameState(
    val fullModuleName: String,
    val namePart: String,
    val selectedModuleLevelIndex: Int,
    val selectedModuleDomainIndex: Int,
)
