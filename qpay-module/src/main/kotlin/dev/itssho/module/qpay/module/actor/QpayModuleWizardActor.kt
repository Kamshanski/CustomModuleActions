package dev.itssho.module.qpay.module.actor

import dev.itssho.module.core.actor.BaseActor
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.qpay.module.actor.di.component.NameKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayCreateKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayDeprecatedNameKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayStructureKoinDi
import dev.itssho.module.qpay.module.actor.di.component.SelectionKoinDi
import dev.itssho.module.qpay.module.actor.di.makeDi
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.common.domain.usecase.GetSettingsUseCase
import dev.itssho.module.qpay.module.create.actor.QpayCreateStep
import dev.itssho.module.qpay.module.name.actor.NameStep
import dev.itssho.module.qpay.module.name.deprecated.actor.QpayDeprecatedNameStep
import dev.itssho.module.qpay.module.name.deprecated.presentation.QpayNameStepResult
import dev.itssho.module.qpay.module.name.presentation.model.NameStepResult
import dev.itssho.module.qpay.module.selection.actor.SelectionStep
import dev.itssho.module.qpay.module.selection.presentation.SelectionStepResult
import dev.itssho.module.qpay.module.structure.actor.QpayStructureStep
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureStepResult
import dev.itssho.module.shared.preferences.Settings
import dev.itssho.module.util.koin.use
import reflection.castOrNull

class QpayModuleWizardActor(jbContext: JBContext) : BaseActor(jbContext) {

	val di = makeDi(jbContext).koin

	override suspend fun runAction() {
		val valueStorage = FullyEditableValueStorage()

		val moduleAction = runSelectionStep(valueStorage) ?: return

		val moduleName = if (getSettings().useQpayNameStep) {
			runQpayNameStep() ?: return
		} else {
			runNameStep() ?: return
		}

		val structure = runStructureStep(moduleName) ?: return

		runCreateStep(moduleName, structure)
	}

	private fun getSettings(): Settings {
		val settings = di.get<GetSettingsUseCase>().invoke()
		return settings
	}

	private suspend fun runSelectionStep(valueStorage: FullyEditableValueStorage): ModuleAction? =
		di.get<SelectionKoinDi>()
			.use { selectionDi -> SelectionStep(valueStorage, selectionDi) }
			.castOrNull<SelectionStepResult.Compilation>()
			?.moduleAction

	private suspend fun runQpayNameStep(): String? =
		di.get<QpayDeprecatedNameKoinDi>()
			.use { nameDi -> QpayDeprecatedNameStep(nameDi) }
			.castOrNull<QpayNameStepResult.Name>()
			?.name

	private suspend fun runNameStep(): String? =
		di.get<NameKoinDi>()
			.use { nameDi -> NameStep(nameDi) }
			.castOrNull<NameStepResult.Name>()
			?.name

	private suspend fun runStructureStep(moduleName: String): HierarchyObject? =
		di.get<QpayStructureKoinDi>()
			.use { structureDi -> QpayStructureStep(moduleName, structureDi) }
			.castOrNull<QpayStructureStepResult.Structure>()
			?.filesFoldersHierarchy

	private suspend fun runCreateStep(moduleName: String, structure: HierarchyObject) {
		di.get<QpayCreateKoinDi>()
			.use { createDi -> QpayCreateStep(moduleName, structure, createDi) }
	}
}