package dev.itssho.module.qpay.module.structure.di

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureViewModel
import dev.itssho.module.qpay.module.structure.ui.StructureUi
import dev.itssho.module.util.koin.LocalKoinScope
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf

class QpayStructureKoinDi(
	koin: Koin,
	valueStorage: FullyEditableValueStorage,
	moduleAction: ModuleAction,
	moduleName: String,
) : LocalKoinScope(koin), QpayStructureDi {

	companion object {

		fun Koin.getStructureKoinDi(
			valueStorage: FullyEditableValueStorage,
			moduleAction: ModuleAction,
			moduleName: String,
		): QpayStructureKoinDi =
			get { parametersOf(valueStorage, moduleAction, moduleName) }
	}

	init {
		scope.declare(moduleName, ModuleNameQ)
		scope.declare(valueStorage, secondaryTypes = listOf(ValueStorage::class, MutableValueStorage::class))
		scope.declare(moduleAction)
	}

	override fun getUi(): StructureUi = scope.get()

	override fun getViewModel(): QpayStructureViewModel = scope.get()
}