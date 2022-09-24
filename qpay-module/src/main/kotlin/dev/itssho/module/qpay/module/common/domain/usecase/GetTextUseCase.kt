package dev.itssho.module.qpay.module.common.domain.usecase

import dev.itssho.module.hierarchy.text.Text

class GetTextUseCase(
	private val getModuleNameUseCase: GetModuleNameUseCase,
) {

	operator fun invoke(text: Text) : String =
		when (text) {
			is Text.Const -> text.string
			is Text.Complex -> text.text.asSequence().map { invoke(it) }.joinToString()
			is Text.Var.MODULE_NAME -> getModuleNameUseCase().joinToString(separator = "-") // TODO сделать Text.Var.MODULE_NAME классом и в конструкторе передавать transform: (List<String>) -> String
			else -> TODO("Custom text variables are not implemented yet")
		}
}