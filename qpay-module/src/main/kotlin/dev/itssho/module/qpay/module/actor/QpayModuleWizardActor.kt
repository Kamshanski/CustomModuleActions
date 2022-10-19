package dev.itssho.module.qpay.module.actor

import dev.itssho.module.core.actor.BaseActor
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.qpay.module.actor.di.component.NameKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayCreateKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayDeprecatedNameKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayPreparationKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayStructureKoinDi
import dev.itssho.module.qpay.module.actor.di.makeDi
import dev.itssho.module.qpay.module.create.actor.QpayCreateStep
import dev.itssho.module.qpay.module.name.actor.NameStep
import dev.itssho.module.qpay.module.name.deprecated.actor.QpayDeprecatedNameStep
import dev.itssho.module.qpay.module.name.deprecated.presentation.QpayNameStepResult
import dev.itssho.module.qpay.module.name.presentation.model.NameStepResult
import dev.itssho.module.qpay.module.preparation.actor.QpayPreparationStep
import dev.itssho.module.qpay.module.preparation.presentation.PreparationStepResult
import dev.itssho.module.qpay.module.structure.actor.QpayStructureStep
import dev.itssho.module.qpay.module.structure.presentation.QpayStructureStepResult
import dev.itssho.module.util.koin.use
import java.nio.file.Files
import java.nio.file.Path

class QpayModuleWizardActor(jbContext: JBContext) : BaseActor(jbContext) {

	val di = makeDi(jbContext).koin

	override suspend fun runAction() {

		val preparationResult = di.get<QpayPreparationKoinDi>().use { preparationDi ->
			QpayPreparationStep(preparationDi)
		}

		when (preparationResult) {
			is PreparationStepResult.Success -> Unit
			is PreparationStepResult.Failure -> return
		}

		val configMap = readConfig()
		val useDelimiterlessNameStep = configMap.getOrDefault("delimiterlessNameStep", "false")

		val moduleName = if (useDelimiterlessNameStep.toBoolean()) {
			val nameResult = di.get<NameKoinDi>().use { nameDi ->
				NameStep(nameDi)
			}
			when (nameResult) {
				is NameStepResult.Name    -> nameResult.name
				is NameStepResult.Nothing -> return
			}
		} else {
			val nameResult = di.get<QpayDeprecatedNameKoinDi>().use { nameDi ->
				QpayDeprecatedNameStep(nameDi)
			}
			when (nameResult) {
				is QpayNameStepResult.Name    -> nameResult.name
				is QpayNameStepResult.Nothing -> return
			}
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

private fun readConfig(): Map<String, String> {
	val lines = Files.readAllLines(Path.of("C:\\Users\\Dawan\\Desktop\\Config.txt"))
	val configMap = lines
		.map { it.split("=") }
		.associate { it[0] to it[1] }
	return configMap
}