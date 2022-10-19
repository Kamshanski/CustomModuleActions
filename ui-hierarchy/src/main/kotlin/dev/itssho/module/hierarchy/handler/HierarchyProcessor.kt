package dev.itssho.module.hierarchy.handler

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.HierarchyObject.HOFile
import dev.itssho.module.hierarchy.HierarchyObject.HOLabel
import dev.itssho.module.hierarchy.HierarchyObject.HOSelector
import dev.itssho.module.hierarchy.HierarchyObject.HOTreeCheck
import dev.itssho.module.hierarchy.attr.Directory
import dev.itssho.module.hierarchy.controller.Controller
import dev.itssho.module.hierarchy.handler.util.DirUtil.extractDirRecursively
import dev.itssho.module.hierarchy.handler.util.FileUtil.getContent
import dev.itssho.module.hierarchy.handler.util.FileUtil.getFileExtensionPart
import dev.itssho.module.hierarchy.handler.util.FileUtil.getFileName
import dev.itssho.module.hierarchy.storage.MutableValueStorage

/** От него нужно наследоваться. Может быть синглтоном */
@Suppress("MemberVisibilityCanBePrivate")
open class HierarchyProcessor {

	/** ВЫзывается 1 раз при обработке получившегося дерева */
	open fun handle(ho: HierarchyObject, valueStorage: MutableValueStorage, controller: Controller) {
		when (ho) {
			is HOFile      -> handleFileDefault(ho, valueStorage, controller)

			is HOLabel     -> { /* Do nothing */ }

			is HOSelector  -> handleSelectorDefault(ho, valueStorage, controller)
			is HOTreeCheck -> handleDirectoryDefault(ho, valueStorage, controller)
		}
	}

	fun handleFileDefault(ho: HOFile, valueStorage: MutableValueStorage, controller: Controller) {
		val directory = extractDirRecursively(ho, valueStorage, ::interpretDirDefault)

		val extension = getFileExtensionPart(ho)
		val fileName = getFileName(ho)
		val fullFileName = fileName + extension

		val content = getContent(ho, directory, fileName, extension, valueStorage)

		controller.createFile(directory, fullFileName, content)
	}

	fun handleSelectorDefault(ho: HOSelector, valueStorage: MutableValueStorage, controller: Controller) {
		val directory = extractDirRecursively(ho, valueStorage, ::interpretDirDefault)

		val extension = getFileExtensionPart(ho)
		val fileName = getFileName(ho)
		val fullFileName = fileName + extension

		val content = getContent(ho, directory, fileName, extension, valueStorage)

		controller.createFile(directory, fullFileName, content)
	}

	fun handleDirectoryDefault(ho: HOTreeCheck, valueStorage: MutableValueStorage, controller: Controller) {
		val directory = extractDirRecursively(ho, valueStorage, ::interpretDirDefault)

		controller.createDirectory(directory)
	}

	open fun interpretDirDefault(directory: Directory.Chain, ho: HierarchyObject, moduleName: String): List<String> =
		when (directory) {
			is Directory.Chain.CONST      -> directory.chain
			is Directory.Chain.CALCULATED -> directory.calculate(ho)
		}
}