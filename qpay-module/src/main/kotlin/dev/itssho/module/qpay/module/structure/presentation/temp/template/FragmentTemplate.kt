package dev.itssho.module.qpay.module.structure.presentation.temp.template

import dev.itssho.module.hierarchy.attr.FileTemplate
import dev.itssho.module.hierarchy.storage.ValueStorage

class FragmentTemplate : FileTemplate.Template("FRAGMENT_TEMPLATE_NAME") {

	override fun compile(folder: List<String>, fileName: String, fileExtension: String, valueStorage: ValueStorage): String =
		"""
			package ${folder.joinToString(separator = ".")}
			
			import java.lang.Runnable

			class $fileName(

			) : Runnable() {
				
				override fun run() { 
					// TODO Fragment
				}
			}
			
		""".trimIndent()
}