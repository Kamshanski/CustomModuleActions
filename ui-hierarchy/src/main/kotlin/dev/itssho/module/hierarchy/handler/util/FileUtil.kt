package dev.itssho.module.hierarchy.handler.util

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.attr.FileExtension
import dev.itssho.module.hierarchy.attr.FileTemplate
import dev.itssho.module.hierarchy.extension.attributeOrNull
import dev.itssho.module.hierarchy.storage.ValueStorage

object FileUtil {

	fun getFileExtensionPart(ho: HierarchyObject): String {
		val ext = ho.attributeOrNull<FileExtension>()?.string

		if (ext != null) {
			return ".$ext"
		} else {
			return ""
		}
	}

	fun getFileName(ho: HierarchyObject.HOFile): String =
		ho.text

	fun getFileName(ho: HierarchyObject.HOSelector): String =
		ho.items[ho.selectedIndex]

	fun getContent(ho: HierarchyObject, directory: List<String>, fileName: String, fileExtension: String, valueStorage: ValueStorage): String {
		val templateAttr = ho.attributeOrNull<FileTemplate>()
		templateAttr!!

		val content = templateAttr.template.compile(directory, fileName, fileExtension, valueStorage)

		return content
	}
}