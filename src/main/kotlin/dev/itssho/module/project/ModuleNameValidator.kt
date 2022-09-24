package dev.itssho.module.project

import com.intellij.openapi.ui.InputValidator

abstract class ModuleNameValidator: InputValidator {

    abstract fun validate(inputString: String?): Boolean

    final override fun checkInput(inputString: String?): Boolean = validate(inputString)

    final override fun canClose(inputString: String?): Boolean = validate(inputString)
}