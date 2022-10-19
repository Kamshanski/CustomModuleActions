package dev.itssho.module.qpay.module.actor

import dev.itssho.module.core.actor.BaseActor
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.qpay.module.actor.di.component.QpayCreateKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayDeprecatedNameKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayPreparationKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayStructureKoinDi
import dev.itssho.module.qpay.module.actor.di.makeDi
import dev.itssho.module.qpay.module.create.actor.QpayCreateStep
import dev.itssho.module.qpay.module.name.deprecated.actor.QpayDeprecatedNameStep
import dev.itssho.module.qpay.module.name.deprecated.presentation.QpayNameStepResult
import dev.itssho.module.qpay.module.preparation.actor.QpayPreparationStep
import dev.itssho.module.qpay.module.preparation.presentation.PreparationStepResult
import dev.itssho.module.qpay.module.structure.actor.QpayStructureStep
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureStepResult
import dev.itssho.module.util.koin.use

class QpayModuleWizardActor(jbContext: JBContext) : BaseActor(jbContext) {

	val di = makeDi(jbContext).koin

	override suspend fun runAction() {

		val preparationResult = di.get<QpayPreparationKoinDi>().use { preparationDi ->
			QpayPreparationStep(preparationDi)
		}

		when (preparationResult) {
			is PreparationStepResult.Success -> {}
			is PreparationStepResult.Failure -> return
		}

		val creationResult = di.get<QpayDeprecatedNameKoinDi>().use { nameDi ->
			QpayDeprecatedNameStep(nameDi)
		}
		val moduleName = when (creationResult) {
			is QpayNameStepResult.Name    -> creationResult.name
			is QpayNameStepResult.Nothing -> return
		}

		val structureResult = di.get<QpayStructureKoinDi>().use { structureDi ->
			QpayStructureStep(moduleName, structureDi)
		}
		val structure = when (structureResult) {
			is QpayStructureStepResult.Structure -> structureResult.filesFoldersHierarchy
			is QpayStructureStepResult.Nothing   -> return
		}

		di.get<QpayCreateKoinDi>().use { createDi ->
			QpayCreateStep(moduleName, structure, createDi)
		}
	}
}