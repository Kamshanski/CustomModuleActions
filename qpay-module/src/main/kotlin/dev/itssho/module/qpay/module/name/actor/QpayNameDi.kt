package dev.itssho.module.qpay.module.name.actor

import dev.itssho.module.qpay.module.name.presentation.QpayNameViewModel
import dev.itssho.module.qpay.module.name.ui.NameUI
import kotlinx.coroutines.CoroutineScope

interface QpayNameDi {

	fun getUi(): NameUI

	fun getViewModel(): QpayNameViewModel

	fun getUiScope(): CoroutineScope
}