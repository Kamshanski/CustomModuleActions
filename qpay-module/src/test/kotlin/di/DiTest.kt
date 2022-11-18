@file:Suppress("MemberVisibilityCanBePrivate")

package di

import com.intellij.ide.script.IdeScriptEngineManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import dev.itssho.module.core.context.ProjectWindowClickContext
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.qpay.module.actor.di.ModuleHost
import dev.itssho.module.qpay.module.actor.di.StepModuleHost
import dev.itssho.module.qpay.module.actor.di.makeDi
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.create.di.QpayCreateKoinDi.Companion.getCreateKoinDi
import dev.itssho.module.qpay.module.name.deprecated.di.QpayDeprecatedNameKoinDi.Companion.getQpayNameKoinDi
import dev.itssho.module.qpay.module.name.di.NameKoinDi.Companion.getNameKoinDi
import dev.itssho.module.qpay.module.selection.di.SelectionKoinDi.Companion.getSelectionKoinDi
import dev.itssho.module.qpay.module.structure.di.QpayStructureKoinDi.Companion.getStructureKoinDi
import dev.itssho.module.test.koin.KoinMockExtension
import dev.itssho.module.test.koin.scopeDynamicTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@KoinInternalApi
@ExtendWith(KoinMockExtension::class)
class DiTest {

	val structure: HierarchyObject = mock()
	val valueStorage: FullyEditableValueStorage = mock()
	val moduleAction: ModuleAction = mock()
	val moduleName = "ModuleName"

	val context: ProjectWindowClickContext = mock()
	val project: Project = mock()
	val rootDirectory: PsiDirectory = mock()
	val rootDirectoryVirtualFile: VirtualFile = mock()

	@BeforeEach
	fun before() {
		whenever(context.ideProject).thenReturn(project)

		whenever(context.mainFolderPsiDirectory).thenReturn(rootDirectory)
		whenever(rootDirectory.virtualFile).thenReturn(rootDirectoryVirtualFile)
		whenever(rootDirectoryVirtualFile.path).thenReturn("abvgd")
	}

	@Test
	fun verifyFullGraph() {
		val koinApp = makeKoinDi().apply {
			koin.declare(valueStorage, secondaryTypes = listOf(ValueStorage::class, MutableValueStorage::class))
			koin.declare(moduleAction)
			koin.declare(moduleName)
			koin.declare(structure)
		}

		koinApp.checkModules()
	}

	private fun makeKoinDi() = makeDi(context)

	/** Если верхнеуровневая сущность UI сможет создаться, то для этого скоупа всё ок.
	 * Этот тест более честный по скоупам, т.к. не проверяет scoped-сущности вне самого скоупа, как это делает checkModules */
	@TestFactory
	fun verifyEachScope(): Collection<DynamicTest> {
		return listOf(
			scopeDynamicTest(makeTestKoinDi { selectionModule }.koin.getSelectionKoinDi(valueStorage)),
			scopeDynamicTest(makeTestKoinDi { qpayNameModule }.koin.getQpayNameKoinDi(valueStorage, moduleAction)),
			scopeDynamicTest(makeTestKoinDi { nameModule }.koin.getNameKoinDi(valueStorage, moduleAction)),
			scopeDynamicTest(makeTestKoinDi { structureModule }.koin.getStructureKoinDi(valueStorage, moduleAction, moduleName)),
			scopeDynamicTest(makeTestKoinDi { createModule }.koin.getCreateKoinDi(valueStorage, moduleAction, moduleName, structure)),
		)
	}

	private fun makeTestKoinDi(filter: StepModuleHost.() -> Module) = koinApplication {
		allowOverride(false)
		val host = ModuleHost(context, koin)
		val stepHost = StepModuleHost(host)

		modules(stepHost.filter())
	}
}