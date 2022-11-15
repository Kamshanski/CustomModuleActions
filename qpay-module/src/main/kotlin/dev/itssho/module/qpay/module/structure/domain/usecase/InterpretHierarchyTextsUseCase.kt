package dev.itssho.module.qpay.module.structure.domain.usecase

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.extension.forEach
import dev.itssho.module.hierarchy.text.Text
import dev.itssho.module.hierarchy.text.Textual

class InterpretHierarchyTextsUseCase {

	operator fun invoke(rootHo: HierarchyObject): Map<Text, String> {
		val texts = mutableMapOf<Text, String>()

		rootHo.forEach { ho ->
			ho.attrs.forEach { attr ->
				if (attr is Textual) {
					val text = mapText(attr.text)
					texts[attr.text] = text
				}
			}
		}

		return texts
	}

	private fun mapText(text: Text): String =
		when (text) {
			is Text.Const   -> text.string
			is Text.Complex -> text.text.asSequence().map { mapText(it) }.joinToString(separator = "")
			is Text.Var     -> TODO("Custom text variables are not implemented yet")
		}
}