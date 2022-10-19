package dev.itssho.module.qpay.module.actor.di.component

import dev.itssho.module.qpay.module.preparation.actor.QpayPreparationDi
import dev.itssho.module.qpay.module.preparation.presentation.PreparationViewModel
import dev.itssho.module.qpay.module.preparation.ui.PreparationUi
import dev.itssho.module.qpay.module.structure.actor.di.UiScopeQ
import dev.itssho.module.util.koin.LocalKoinScope
import kotlinx.coroutines.CoroutineScope
import org.koin.core.Koin

class QpayPreparationKoinDi(koin: Koin) : LocalKoinScope(koin), QpayPreparationDi {

	override fun getUi(): PreparationUi = scope.get()

	override fun getViewModel(): PreparationViewModel = scope.get()

	override fun getUiScope(): CoroutineScope = scope.get(UiScopeQ)
}