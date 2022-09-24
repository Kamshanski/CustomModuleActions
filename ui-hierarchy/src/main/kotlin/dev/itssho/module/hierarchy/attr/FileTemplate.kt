package dev.itssho.module.hierarchy.attr

import dev.itssho.module.hierarchy.storage.ValueStorage

data class FileTemplate(val template: Template) : Attr.StandardAttr() {

	abstract class Template(val uniqueName: String) {

		abstract fun compile(folder: List<String>, fileName: String, fileExtension: String, valueStorage: ValueStorage): String

		final override fun equals(other: Any?): Boolean {
			if (this === other) return true
			if (other !is Template) return false

			if (uniqueName != other.uniqueName) return false

			return true
		}

		final override fun hashCode(): Int {
			return uniqueName.hashCode()
		}
	}
}
