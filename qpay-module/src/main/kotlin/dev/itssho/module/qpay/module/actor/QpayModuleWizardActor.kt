package dev.itssho.module.qpay.module.actor

import dev.itssho.module.core.actor.BaseActor
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.qpay.module.actor.di.component.NameKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayCreateKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayDeprecatedNameKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayPreparationKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayStructureKoinDi
import dev.itssho.module.qpay.module.actor.di.makeDi
import dev.itssho.module.qpay.module.common.domain.usecase.GetSettingsUseCase
import dev.itssho.module.qpay.module.create.actor.QpayCreateStep
import dev.itssho.module.qpay.module.name.actor.NameStep
import dev.itssho.module.qpay.module.name.deprecated.actor.QpayDeprecatedNameStep
import dev.itssho.module.qpay.module.name.deprecated.presentation.QpayNameStepResult
import dev.itssho.module.qpay.module.name.presentation.model.NameStepResult
import dev.itssho.module.qpay.module.preparation.actor.QpayPreparationStep
import dev.itssho.module.qpay.module.preparation.presentation.PreparationStepResult
import dev.itssho.module.qpay.module.structure.actor.QpayStructureStep
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureStepResult
import dev.itssho.module.shared.preferences.Settings
import dev.itssho.module.util.koin.use
import reflection.castOrNull

class QpayModuleWizardActor(jbContext: JBContext) : BaseActor(jbContext) {

	val di = makeDi(jbContext).koin

	override suspend fun runAction() {

		runPreparationStep() ?: return

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

	private suspend fun runPreparationStep(): Unit? =
		di.get<QpayPreparationKoinDi>()
			.use { preparationDi -> QpayPreparationStep(preparationDi) }
			.castOrNull<PreparationStepResult.Success>()
			?.let { Unit }

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