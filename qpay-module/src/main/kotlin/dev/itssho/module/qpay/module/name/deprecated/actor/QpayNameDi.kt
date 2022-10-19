package dev.itssho.module.qpay.module.name.deprecated.actor

import dev.itssho.module.qpay.module.name.deprecated.presentation.QpayNameViewModel
import dev.itssho.module.qpay.module.name.deprecated.ui.NameUI
import kotlinx.coroutines.CoroutineScope

interface QpayNameDi {

	fun getUi(): NameUI

	fun getViewModel(): QpayNameViewModel

	fun getUiScope(): CoroutineScope
}