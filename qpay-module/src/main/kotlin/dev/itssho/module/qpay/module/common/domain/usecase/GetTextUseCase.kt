package dev.itssho.module.qpay.module.common.domain.usecase

import dev.itssho.module.hierarchy.text.Text

// TODO Работа с valueStorage должна быть модуле ui-hierarchy
// TODO После перенова в ui-hierarchy переименовать в InterpretText
class GetTextUseCase {

	operator fun invoke(text: Text): String =
		when (text) {
			is Text.Const   -> text.string
			is Text.Complex -> text.text.asSequence().map { invoke(it) }.joinToString()
			else            -> TODO("Custom text variables are not implemented yet")
		}
}