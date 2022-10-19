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

	/** Вытаскивает все атрибуты DirChain от ho до последненго родителя */
	fun extractDirRecursively(
		ho: HierarchyObject,
		valueStorage: ValueStorage,
		interpretDir: DirInterpreter,
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