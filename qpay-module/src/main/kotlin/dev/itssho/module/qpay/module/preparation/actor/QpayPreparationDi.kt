package dev.itssho.module.qpay.module.preparation.actor

import dev.itssho.module.qpay.module.preparation.presentation.PreparationViewModel
import dev.itssho.module.qpay.module.preparation.ui.PreparationUi
import kotlinx.coroutines.CoroutineScope

interface QpayPreparationDi {

	fun getUi(): PreparationUi

	fun getViewModel(): PreparationViewModel

	fun getUiScope(): CoroutineScope
}