package dev.itssho.module.feature.qpay.module.common.presentation

sealed class Act(val id: String, val text: String? = null, val icon: ByteArray? = null) {

    init {
        require(text != null || icon != null) { "Act must have text or icon" }
    }
}

class DeleteAct : Act("delete", "-")
class AddAct(text: String) : Act("add", text)

fun onlyAddAct(): List<Act> = onlyAddAct("+")
fun onlyAddAct(text: String): List<Act> = listOf(AddAct(text))