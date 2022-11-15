package dev.itssho.module.qpay.module.name.actor

import dev.itssho.module.qpay.module.name.presentation.NameViewModel
import dev.itssho.module.qpay.module.name.ui.NameUi
import kotlinx.coroutines.CoroutineScope

interface NameDi {

	fun getUi(): NameUi

	fun getViewModel(): NameViewModel

	fun getUiScope(): CoroutineScope
}