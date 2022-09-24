package dev.itssho.module.qpay.module.structure.presentation.temp.template

import dev.itssho.module.hierarchy.attr.FileTemplate
import dev.itssho.module.hierarchy.storage.ValueStorage

class StringsTemplate : FileTemplate.Template("VIEW_MODEL_TEMPLATE_NAME") {

	override fun compile(folder: List<String>, fileName: String, fileExtension: String, valueStorage: ValueStorage): String =
		"""
			<string name="name">Strings</string>
			<string name="ext">xml</string>
		""".trimIndent()
}