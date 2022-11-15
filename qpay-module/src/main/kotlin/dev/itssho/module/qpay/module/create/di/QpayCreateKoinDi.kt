package dev.itssho.module.qpay.module.create.di

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.create.presentation.QpayCreateViewModel
import dev.itssho.module.qpay.module.create.ui.CreateUi
import dev.itssho.module.qpay.module.structure.di.ModuleNameQ
import dev.itssho.module.qpay.module.structure.di.UiScopeQ
import dev.itssho.module.util.koin.LocalKoinScope
import kotlinx.coroutines.CoroutineScope
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf

class QpayCreateKoinDi(
	koin: Koin,
	valueStorage: FullyEditableValueStorage,
	moduleAction: ModuleAction,
	moduleName: String,
	structure: HierarchyObject,
) : LocalKoinScope(koin), QpayCreateDi {

	companion object {

		fun Koin.getCreateKoinDi(
			valueStorage: FullyEditableValueStorage,
			moduleAction: ModuleAction,
			moduleName: String,
			structure: HierarchyObject,
		): QpayCreateKoinDi =
			get { parametersOf(valueStorage, moduleAction, moduleName, structure) }
	}

	init {
		scope.declare(moduleName, ModuleNameQ)
		scope.declare(valueStorage, secondaryTypes = listOf(ValueStorage::class, MutableValueStorage::class))
		scope.declare(moduleAction)
		scope.declare(structure)
	}

	override fun getUi(): CreateUi = scope.get()

	override fun getViewModel(): QpayCreateViewModel = scope.get()

	override fun getUiScope(): CoroutineScope = scope.get(UiScopeQ)
}