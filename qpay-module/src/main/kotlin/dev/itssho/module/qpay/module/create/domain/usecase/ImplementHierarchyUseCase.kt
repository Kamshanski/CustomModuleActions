package dev.itssho.module.qpay.module.create.domain.usecase

import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.controller.Controller
import dev.itssho.module.hierarchy.initializer.Initializer
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.create.domain.hierarchy.HierarchyProcessorProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

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
    private val hierarchyProcessorProvider: HierarchyProcessorProvider,
    private val controller: Controller,
    private val initializer: Initializer,
    private val valueStorage: MutableValueStorage,
) {

    /**
     * Implements only given [ho]. Children are not considered
     */
    @Throws(RuntimeException::class)
    suspend operator fun invoke(ho: HierarchyObject) {
        val hierarchyProcessor = hierarchyProcessorProvider.get()
        initializer.initialize(valueStorage)

        hierarchyProcessor.handle(ho, valueStorage, controller)

        // TODO реализовать логику создания файлов и папок и редактирования файлов
        var currentTime = System.currentTimeMillis()
        val endTimeTime = currentTime + 100
        while (currentTime < endTimeTime) {
            delay(1)
            currentTime = System.currentTimeMillis()
        }
    }
}