package dev.itssho.module.qpay.module.actor.di.component

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.qpay.module.create.actor.QpayCreateDi
import dev.itssho.module.qpay.module.create.presentation.QpayCreateViewModel
import dev.itssho.module.qpay.module.create.ui.QpayCreateUi
import dev.itssho.module.qpay.module.structure.actor.di.NavScopeQ
import dev.itssho.module.qpay.module.structure.actor.di.UiScopeQ
import dev.itssho.module.util.koin.LocalKoinScope
import kotlinx.coroutines.CoroutineScope
import org.koin.core.Koin

class QpayCreateKoinDi(koin: Koin) : LocalKoinScope(koin), QpayCreateDi {

	override fun getUi(): QpayCreateUi = scope.get()

	override fun getViewModel(): QpayCreateViewModel = scope.get()

	override fun getUiScope(): CoroutineScope = scope.get(UiScopeQ)

	override fun insertStructure(structure: HierarchyObject) {
		scope.declare(structure)
	}
}