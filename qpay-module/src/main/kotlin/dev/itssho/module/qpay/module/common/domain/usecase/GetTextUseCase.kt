package dev.itssho.module.qpay.module.common.domain.usecase

import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.hierarchy.storage.moduleName
import dev.itssho.module.hierarchy.text.Text

// TODO Работа с valueStorage должна быть модуле ui-hierarchy
// TODO После перенова в ui-hierarchy переименовать в InterpretText
class GetTextUseCase(
	private val valueStorage: ValueStorage,
) {

	operator fun invoke(text: Text): String =
		when (text) {
			is Text.Const           -> text.string
			is Text.Complex         -> text.text.asSequence().map { invoke(it) }.joinToString()
			is Text.Var.MODULE_NAME -> valueStorage.moduleName // TODO (Старая тудушка) сделать Text.Var.MODULE_NAME классом и в конструкторе передавать transform: (List<String>) -> String
			else                    -> TODO("Custom text variables are not implemented yet")
		}
}