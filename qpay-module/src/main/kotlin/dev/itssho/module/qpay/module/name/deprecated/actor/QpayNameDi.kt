package dev.itssho.module.qpay.module.name.deprecated.actor

import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.name.deprecated.presentation.QpayNameViewModel
import dev.itssho.module.qpay.module.name.deprecated.ui.NameUI
import kotlinx.coroutines.CoroutineScope

interface QpayNameDi {

	fun getUi(): NameUI

	fun getViewModel(): QpayNameViewModel

	fun getUiScope(): CoroutineScope

	fun insertValueStorage(valueStorage: FullyEditableValueStorage)

	fun insertModuleAction(moduleAction: ModuleAction)
}