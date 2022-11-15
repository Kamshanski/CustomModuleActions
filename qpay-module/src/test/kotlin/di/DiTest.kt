@file:Suppress("MemberVisibilityCanBePrivate")

package di

import com.intellij.ide.script.IdeScriptEngineManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.hierarchy.importing.ModuleAction
import dev.itssho.module.hierarchy.storage.MutableValueStorage
import dev.itssho.module.hierarchy.storage.ValueStorage
import dev.itssho.module.qpay.module.actor.di.makeDi
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.qpay.module.create.di.QpayCreateKoinDi.Companion.getCreateKoinDi
import dev.itssho.module.qpay.module.name.deprecated.di.QpayDeprecatedNameKoinDi.Companion.getQpayNameKoinDi
import dev.itssho.module.qpay.module.name.di.NameKoinDi.Companion.getNameKoinDi
import dev.itssho.module.qpay.module.selection.di.SelectionKoinDi.Companion.getSelectionKoinDi
import dev.itssho.module.qpay.module.structure.di.QpayStructureKoinDi.Companion.getStructureKoinDi
import dev.itssho.module.service.action.module.ModuleActionService
import dev.itssho.module.util.koin.LocalKoinScope
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.annotation.KoinInternalApi
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.junit5.mock.MockProviderExtension
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@KoinInternalApi
class DiTest : KoinTest {

	@RegisterExtension
	val mockProvider = MockProviderExtension.create { clazz ->
		// Setup your nock framework
		Mockito.mock(clazz.java)
	}

	val structure: HierarchyObject = mock()
	val valueStorage: FullyEditableValueStorage = mock()
	val moduleAction: ModuleAction = mock()
	val moduleName = "ModuleName"

	val ideScriptEngineManager: IdeScriptEngineManager = mock()
	val moduleActionService: ModuleActionService = mock()

	val context: JBContext = mock()
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

	/** Если верхнеуровневая сущность UI сможет создаться, то для этого скоупа всё ок.
	 * Этот тест более честный по скоупам, т.к. не проверяет scoped-сущности вне самого скоупа, как это делает checkModules */
	@TestFactory
	fun verifyEachScope(): Collection<DynamicTest> {
		val koinApp = makeKoinDi()
		val koin = koinApp.koin

		return listOf(
			stepScopeDynamicTest(koin.getSelectionKoinDi(valueStorage)),
			stepScopeDynamicTest(koin.getQpayNameKoinDi(valueStorage, moduleAction)),
			stepScopeDynamicTest(koin.getNameKoinDi(valueStorage, moduleAction)),
			stepScopeDynamicTest(koin.getStructureKoinDi(valueStorage, moduleAction, moduleName)),
			stepScopeDynamicTest(koin.getCreateKoinDi(valueStorage, moduleAction, moduleName, structure)),
		)
	}

	private fun makeKoinDi() = makeDi(context).apply {
		koin.declare(ideScriptEngineManager)
		koin.declare(moduleActionService)
	}

	private fun stepScopeDynamicTest(scopeHolder: LocalKoinScope): DynamicTest {
		val testName = scopeHolder::class.simpleName!!
		return DynamicTest.dynamicTest(testName) {
			assertScopedItemsAvailable(scopeHolder)
		}
	}

	private fun assertScopedItemsAvailable(scopeHolder: LocalKoinScope) {
		val scope = scopeHolder.scope
		val scopeValues = scope.getKoin().instanceRegistry.instances.filter { it.value.beanDefinition.scopeQualifier == scope.scopeQualifier }.values
		scopeValues.forEach {
			scope.get<Any>(it.beanDefinition.primaryType, it.beanDefinition.qualifier)
			it.beanDefinition.secondaryTypes.forEach { secondaryType -> scope.get<Any>(secondaryType, it.beanDefinition.qualifier) }
		}
	}
}