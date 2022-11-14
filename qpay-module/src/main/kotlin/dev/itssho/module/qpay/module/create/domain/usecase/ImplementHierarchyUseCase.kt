package dev.itssho.module.qpay.module.create.domain.usecase

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.controller.Controller
import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.storage.MutableValueStorage

/**
 * Полезные исходнички
 * https://github.com/JetBrains/intellij-community/blob/15e83320cad839ec2964126d6c075f660280588f/resources/src/idea/JavaActions.xml
 * https://github.com/JetBrains/intellij-community/blob/79bf4b1ae08162fb2154fd0d058d8a8e31fa23a2/java/compiler/impl/src/com/intellij/packaging/impl/ui/actions/PackageFileAction.java
 * com.intellij.ide.actions.CreateDirectoryOrPackageAction
 * com.intellij.ide.actions.CreatePackageHandler
 * com.intellij.ide.actions.CreateDirectoryOrPackageHandler
 * com.intellij.ide.util.PackageChooserDialog
 */

class ImplementHierarchyUseCase(
	private val controller: Controller,
) {

	/**
	 * Implements only given [ho]. Children are not considered
	 */
	@Throws(RuntimeException::class)
	operator fun invoke(
		valueStorage: MutableValueStorage, // TODO временное решение. Убрать после изменения структуры DI
		moduleAction: ModuleAction, // TODO временное решение. Убрать после изменения структуры DI
		ho: HierarchyObject
	) {
		val hierarchyProcessor = moduleAction.hierarchyProcessor

		hierarchyProcessor.handle(ho, valueStorage, controller)
	}
}