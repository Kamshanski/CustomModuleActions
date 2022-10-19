package dev.itssho.module.qpay.module.create.domain.hierarchy

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.HierarchyObject.HOFile
import dev.itssho.module.hierarchy.HierarchyObject.HOLabel
import dev.itssho.module.hierarchy.HierarchyObject.HOSelector
import dev.itssho.module.hierarchy.HierarchyObject.HOTreeCheck
import dev.itssho.module.hierarchy.controller.Controller
import dev.itssho.module.hierarchy.extension.selectedItem
import dev.itssho.module.hierarchy.handler.HierarchyProcessor
import dev.itssho.module.hierarchy.handler.util.DirUtil
import dev.itssho.module.hierarchy.handler.util.FileUtil
import dev.itssho.module.hierarchy.storage.MutableValueStorage

class QpayHierarchyProcessor : HierarchyProcessor() {

	override fun handle(ho: HierarchyObject, valueStorage: MutableValueStorage, controller: Controller) {
		when (ho) {
			is HOFile      -> {
				super.handle(ho, valueStorage, controller)
			}

			is HOLabel     -> {}

			is HOSelector  -> {
				when {
					ho.id.contains("team") -> handleTeam(ho, valueStorage, controller)
					else                   -> super.handle(ho, valueStorage, controller)
				}
			}

			is HOTreeCheck -> {
				when {
					ho.id.contains("mockito")  -> handleMockito(ho)
					ho.id.contains("unitTest") -> handleUnitTest(ho)
					ho.id.contains("build")    -> handleBuild(ho)
					ho.id.contains("readme")   -> handleReadme(ho)
					else                       -> super.handle(ho, valueStorage, controller)
				}
			}
		}
	}

	private fun handleMockito(ho: HOTreeCheck) {
		println("Skip mockito")
	}

	private fun handleUnitTest(ho: HOTreeCheck) {
		println("Skip unitTest")
	}

	private fun handleBuild(ho: HOTreeCheck) {
		println("Skip build")
	}

	private fun handleReadme(ho: HOTreeCheck) {
		println("Skip build")
	}

	private fun handleTeam(ho: HOSelector, valueStorage: MutableValueStorage, controller: Controller) {
		valueStorage.putOrReplace("team", ho.selectedItem)
		val parent = ho.parent
		val directory = DirUtil.extractDirRecursively(parent!!, valueStorage)

		val extension = FileUtil.getFileExtensionPart(ho)
		val fileName = FileUtil.getFileName(ho)
		val fullFileName = fileName + extension

		val content = FileUtil.getContent(ho, directory, fileName, extension, valueStorage)

		controller.createFile(directory, fullFileName, content)
	}
}