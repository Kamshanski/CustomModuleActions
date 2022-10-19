package dev.itssho.module.qpay.module.actor.di.component

import dev.itssho.module.qpay.module.name.actor.QpayNameDi
import dev.itssho.module.qpay.module.name.presentation.QpayNameViewModel
import dev.itssho.module.qpay.module.name.ui.NameUI
import dev.itssho.module.qpay.module.structure.actor.di.UiScopeQ
import dev.itssho.module.util.koin.LocalKoinScope
import kotlinx.coroutines.CoroutineScope
import org.koin.core.Koin

class QpayNameKoinDi(koin: Koin) : LocalKoinScope(koin), QpayNameDi {

	override fun getUi(): NameUI = scope.get()

	override fun getViewModel(): QpayNameViewModel = scope.get()

	override fun getUiScope() = scope.get<CoroutineScope>(UiScopeQ)
}