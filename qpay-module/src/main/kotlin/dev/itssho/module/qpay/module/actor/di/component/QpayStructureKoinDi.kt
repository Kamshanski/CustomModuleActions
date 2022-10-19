package dev.itssho.module.qpay.module.actor.di.component

import dev.itssho.module.qpay.module.structure.actor.di.ModuleNameQ
import dev.itssho.module.qpay.module.structure.actor.di.QpayStructureDi
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureViewModel
import dev.itssho.module.qpay.module.structure.ui.StructureUi
import dev.itssho.module.util.koin.LocalKoinScope
import org.koin.core.Koin

class QpayStructureKoinDi(koin: Koin) : LocalKoinScope(koin), QpayStructureDi {

	override fun getUi(): StructureUi = scope.get()

	override fun getViewModel(): QpayStructureViewModel = scope.get()

	override fun insertModuleName(moduleName: String) {
		scope.declare(moduleName, ModuleNameQ)
	}
}