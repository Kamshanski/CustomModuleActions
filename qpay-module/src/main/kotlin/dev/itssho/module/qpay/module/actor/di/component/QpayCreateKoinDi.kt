package dev.itssho.module.qpay.module.actor.di.component

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.qpay.module.create.actor.QpayCreateDi
import dev.itssho.module.qpay.module.create.presentation.QpayCreateViewModel
import dev.itssho.module.qpay.module.create.ui.CreateUi
import dev.itssho.module.qpay.module.structure.actor.di.ModuleNameQ
import dev.itssho.module.qpay.module.structure.actor.di.UiScopeQ
import dev.itssho.module.util.koin.LocalKoinScope
import kotlinx.coroutines.CoroutineScope
import org.koin.core.Koin

class QpayCreateKoinDi(koin: Koin) : LocalKoinScope(koin), QpayCreateDi {

	override fun getUi(): CreateUi = scope.get()

	override fun getViewModel(): QpayCreateViewModel = scope.get()

	override fun getUiScope(): CoroutineScope = scope.get(UiScopeQ)

	override fun insertModuleName(moduleName: String) {
		scope.declare(moduleName, ModuleNameQ)
	}

	override fun insertStructure(structure: HierarchyObject) {
		scope.declare(structure)
	}
}