package dev.itssho.module.qpay.module.name.actor

import dev.itssho.module.qpay.module.name.presentation.QpayNameViewModel
import dev.itssho.module.qpay.module.name.ui.QpayNameUi
import kotlinx.coroutines.CoroutineScope

interface QpayNameDi {

	fun getUi(): QpayNameUi

	fun getViewModel(): QpayNameViewModel

	fun getNavigationScope(): CoroutineScope

	fun getUiScope(): CoroutineScope
}