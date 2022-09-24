package dev.itssho.module.common.component.chain

open class Separator(val value: String) {

    companion object

    object Space : Separator(" ")
    object Windows : Separator("\\")
    object Minus : Separator("-")
    object Package : Separator(".")
    object Linux : Separator("/")
    object Url : Separator("/")

    override fun toString(): String {
        return "Separator.${this::class.simpleName}($value)"
    }


}