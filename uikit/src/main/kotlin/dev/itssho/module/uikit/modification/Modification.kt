package dev.itssho.module.uikit.modification


sealed interface Modification {

    fun modify(constraints: LinearConstraintsBuilder): LinearConstraintsBuilder
}