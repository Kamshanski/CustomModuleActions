package dev.itssho.module.qpay.module.actor.di.component

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
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

	override fun insertValueStorage(valueStorage: FullyEditableValueStorage) {
		scope.declare(valueStorage, secondaryTypes = listOf(ValueStorage::class, MutableValueStorage::class))
	}

	override fun insertModuleAction(moduleAction: ModuleAction) {
		scope.declare(moduleAction)
	}
}