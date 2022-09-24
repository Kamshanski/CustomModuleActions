package dev.itssho.module.project.qpay

import dev.itssho.module.project.ModuleNameValidator

class QpayNameValidator : ModuleNameValidator() {

    companion object {

        val MODULE_NAME_START = listOf(
            "feature",
            "shared",
            "component",
            "design",
            "util",
        ).map {
            Regex("$it\\W")
        }
    }

    override fun validate(inputString: String?): Boolean {
        val input = inputString.takeIf { !it.isNullOrBlank() } ?: return false

//        if (MODULE_NAME_START.none { nameStart -> nameStart.matchesAt(input, 0) }) {
//            return false
//        }

        return true
    }
}