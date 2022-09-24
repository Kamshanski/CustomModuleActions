package dev.itssho.module.qpay.module.actor

import dev.itssho.module.core.actor.BaseActor
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.core.actor.SwingContext
import dev.itssho.module.qpay.module.actor.di.component.QpayCreateKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayNameKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayStructureKoinDi
import dev.itssho.module.qpay.module.actor.di.makeDi
import dev.itssho.module.qpay.module.create.actor.QpayCreateStep
import dev.itssho.module.qpay.module.name.actor.QpayNameStep
import dev.itssho.module.qpay.module.name.actor.QpayNameStepResult
import dev.itssho.module.qpay.module.structure.actor.QpayStructureStep
import dev.itssho.module.qpay.module.structure.actor.QpayStructureStepResult
import dev.itssho.module.util.koin.use

class QpayModuleWizardActor(jbContext: JBContext? = null, swingContext: SwingContext? = null) : BaseActor(jbContext ?: swingContext ?: error("No Context specified")) {

	val di = makeDi(jbContext, swingContext).koin

	override suspend fun runAction() {

		val creationResult = di.get<QpayNameKoinDi>().use { nameDi ->
			QpayNameStep(context, nameDi)
		}
		val moduleName = when (creationResult) {
			is QpayNameStepResult.Name    -> creationResult.name
			is QpayNameStepResult.Nothing -> return
		}

		val structureResult = di.get<QpayStructureKoinDi>().use { structureDi ->
			QpayStructureStep(context, structureDi)
		}
		val structure = when (structureResult) {
			is QpayStructureStepResult.Structure -> structureResult.filesFoldersHierarchy
			is QpayStructureStepResult.Nothing   -> return
		}

		di.get<QpayCreateKoinDi>().use { createDi ->
			QpayCreateStep(context, structure, createDi)
		}
	}
}