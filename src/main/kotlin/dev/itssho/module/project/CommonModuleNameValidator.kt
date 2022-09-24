package dev.itssho.module.project

import dev.itssho.module.project.qpay.QPAY_SEPARATOR
import dev.itssho.module.util.containsSymbolsCase
import dev.itssho.module.util.containsUpperCase

class CommonModuleNameValidator: ModuleNameValidator() {

    override fun validate(inputString: String?): Boolean {
        if (inputString.isNullOrBlank()) {
            return false
        }
        if (!inputString.first().isLetter()) {
            return false
        }
        if (inputString.containsUpperCase()) {
            return false
        }

        val chain = inputString.split(QPAY_SEPARATOR.value)
        if (chain.any { it.isBlank() || it.containsSymbolsCase() }) {
            return false
        }

        return true
    }

}