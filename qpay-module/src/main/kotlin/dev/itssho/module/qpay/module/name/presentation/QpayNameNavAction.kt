package dev.itssho.module.qpay.module.name.presentation

sealed interface QpayNameNavAction {

	object Nowhere : QpayNameNavAction

	object Close : QpayNameNavAction

	data class Continue(val name: List<String>) : QpayNameNavAction
}