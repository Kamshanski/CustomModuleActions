package dev.itssho.module.qpay.module.actor.di.component

import dev.itssho.module.qpay.module.structure.actor.di.NavScopeQ
import dev.itssho.module.qpay.module.structure.actor.di.QpayStructureDi
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureViewModel
import dev.itssho.module.qpay.module.structure.ui.QpayStructureUi
import dev.itssho.module.util.koin.LocalKoinScope
import kotlinx.coroutines.CoroutineScope
import org.koin.core.Koin

class QpayStructureKoinDi(koin: Koin) : LocalKoinScope(koin), QpayStructureDi {

	override fun getUi(): QpayStructureUi = scope.get()

	override fun getViewModel(): QpayStructureViewModel = scope.get()

	override fun getNavigationScope() = scope.get<CoroutineScope>(NavScopeQ)
}