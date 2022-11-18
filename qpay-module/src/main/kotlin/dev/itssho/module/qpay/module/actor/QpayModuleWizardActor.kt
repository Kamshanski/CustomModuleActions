package dev.itssho.module.qpay.module.actor

import dev.itssho.module.core.actor.BaseActor
import dev.itssho.module.core.context.ProjectWindowClickContext
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.qpay.module.actor.di.makeDi
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.create.actor.QpayCreateStep
import dev.itssho.module.qpay.module.create.di.QpayCreateKoinDi.Companion.getCreateKoinDi
import dev.itssho.module.qpay.module.name.actor.NameStep
import dev.itssho.module.qpay.module.name.deprecated.actor.QpayDeprecatedNameStep
import dev.itssho.module.qpay.module.name.deprecated.di.QpayDeprecatedNameKoinDi.Companion.getQpayNameKoinDi
import dev.itssho.module.qpay.module.name.deprecated.presentation.QpayNameStepResult
import dev.itssho.module.qpay.module.name.di.NameKoinDi.Companion.getNameKoinDi
import dev.itssho.module.qpay.module.name.presentation.model.NameStepResult
import dev.itssho.module.qpay.module.selection.actor.SelectionStep
import dev.itssho.module.qpay.module.selection.di.SelectionKoinDi.Companion.getSelectionKoinDi
import dev.itssho.module.qpay.module.selection.presentation.SelectionStepResult
import dev.itssho.module.qpay.module.structure.actor.QpayStructureStep
import dev.itssho.module.qpay.module.structure.di.QpayStructureKoinDi.Companion.getStructureKoinDi
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureStepResult
import dev.itssho.module.service.preferences.data.persistent.Settings
import dev.itssho.module.service.preferences.domain.usecase.GetSettingsUseCase
import dev.itssho.module.util.koin.use
import reflection.castOrNull

class QpayModuleWizardActor(jbContext: ProjectWindowClickContext) : BaseActor(jbContext) {

	val di = makeDi(jbContext).koin

	override suspend fun runAction() {
		val valueStorage = FullyEditableValueStorage()

		val moduleAction = runSelectionStep(valueStorage) ?: return

		val moduleName = if (getSettings().useQpayNameStep) {
			runQpayNameStep(valueStorage, moduleAction) ?: return
		} else {
			runNameStep(valueStorage, moduleAction) ?: return
		}

		val structure = runStructureStep(moduleName, valueStorage, moduleAction) ?: return

		runCreateStep(moduleName, moduleAction, valueStorage, structure)
	}

	private fun getSettings(): Settings {
		val settings = di.get<GetSettingsUseCase>().invoke()
		return settings
	}

	private suspend fun runSelectionStep(valueStorage: FullyEditableValueStorage): ModuleAction? =
		di.getSelectionKoinDi(valueStorage)
			.use { selectionDi -> SelectionStep(selectionDi) }
			.castOrNull<SelectionStepResult.Compilation>()
			?.moduleAction

	private suspend fun runQpayNameStep(valueStorage: FullyEditableValueStorage, moduleAction: ModuleAction): String? =
		di.getQpayNameKoinDi(valueStorage, moduleAction)
			.use { nameDi -> QpayDeprecatedNameStep(nameDi) }
			.castOrNull<QpayNameStepResult.Name>()
			?.name

	private suspend fun runNameStep(valueStorage: FullyEditableValueStorage, moduleAction: ModuleAction): String? =
		di.getNameKoinDi(valueStorage, moduleAction)
			.use { nameDi -> NameStep(nameDi) }
			.castOrNull<NameStepResult.Name>()
			?.name

	private suspend fun runStructureStep(moduleName: String, valueStorage: FullyEditableValueStorage, moduleAction: ModuleAction): HierarchyObject? =
		di.getStructureKoinDi(valueStorage, moduleAction, moduleName)
			.use { structureDi -> QpayStructureStep(structureDi) }
			.castOrNull<QpayStructureStepResult.Structure>()
			?.filesFoldersHierarchy

	private suspend fun runCreateStep(moduleName: String, moduleAction: ModuleAction, valueStorage: FullyEditableValueStorage, structure: HierarchyObject) {
		di.getCreateKoinDi(valueStorage, moduleAction, moduleName, structure)
			.use { createDi -> QpayCreateStep(createDi) }
	}
}