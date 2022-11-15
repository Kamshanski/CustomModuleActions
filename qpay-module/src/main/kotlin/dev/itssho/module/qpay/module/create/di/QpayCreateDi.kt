package dev.itssho.module.qpay.module.create.di

import dev.itssho.module.qpay.module.create.presentation.QpayCreateViewModel
import dev.itssho.module.qpay.module.create.ui.CreateUi
import kotlinx.coroutines.CoroutineScope

interface QpayCreateDi {

	fun getUi(): CreateUi

	fun getViewModel(): QpayCreateViewModel

	fun getUiScope(): CoroutineScope
}