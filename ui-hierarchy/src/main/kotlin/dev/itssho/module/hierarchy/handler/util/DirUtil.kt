package dev.itssho.module.hierarchy.handler.util

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.attr.DirChain
import dev.itssho.module.hierarchy.extension.attributeOrNull
import dev.itssho.module.hierarchy.extension.hasAttribute
import dev.itssho.module.hierarchy.extension.takeUtillParent
import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.hierarchy.storage.moduleName

@Suppress("MemberVisibilityCanBePrivate")
object DirUtil {

	fun interpretDir(directory: DirChain.Dir, ho: HierarchyObject, moduleName: List<String>): List<String> =
		when (directory) {
			is DirChain.Dir.CUSTOM           -> directory.path.split(directory.delimiter)
			is DirChain.Dir.PERSONAL_ITEM_ID -> ho.personalId.split(directory.delimiter)
			is DirChain.Dir.MODULE_NAME      -> moduleName
		}

	/** Вытаскивает ве атрибуты DirChain от ho до последненго родителя, который */
	fun extractDirRecursively(
		ho: HierarchyObject,
		valueStorage: ValueStorage,
		interpretDir: DirInterpreter = DirUtil::interpretDir,
	): List<String> {
		val moduleName = valueStorage.moduleName

		val dirChainCapableObjects = ho.takeUtillParent(orderFromChildToParent = false) { parent -> !parent.hasAttribute<DirChain>() }

		return dirChainCapableObjects.asSequence()
			.mapNotNull { obj ->
				val dirChain = obj.attributeOrNull<DirChain>() ?: return@mapNotNull null
				dirChain.chain.asSequence()
					.map { dir -> interpretDir(dir, obj, moduleName) }
					.flatten()
			}
			.flatten()
			.toList()
	}
}