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
import dev.itssho.module.qpay.module.actor.di.component.NameKoinDi
import dev.itssho.module.qpay.module.actor.di.component.NameKoinDi.Companion.getNameKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayCreateKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayCreateKoinDi.Companion.getCreateKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayDeprecatedNameKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayDeprecatedNameKoinDi.Companion.getQpayNameKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayStructureKoinDi
import dev.itssho.module.qpay.module.actor.di.component.QpayStructureKoinDi.Companion.getStructureKoinDi
import dev.itssho.module.qpay.module.actor.di.component.SelectionKoinDi
import dev.itssho.module.qpay.module.actor.di.component.SelectionKoinDi.Companion.getSelectionKoinDi
import dev.itssho.module.qpay.module.actor.di.makeDi
import dev.itssho.module.qpay.module.common.domain.storage.FullyEditableValueStorage
import dev.itssho.module.service.action.module.ModuleActionService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.junit5.mock.MockProviderExtension
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertNotNull

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
			koin.declare(structure)
			koin.declare(valueStorage, secondaryTypes = listOf(ValueStorage::class, MutableValueStorage::class))
			koin.declare(moduleAction)
		}

		koinApp.checkModules {
			withParameters<SelectionKoinDi> { parametersOf(valueStorage) }
			withParameters<QpayDeprecatedNameKoinDi> { parametersOf(valueStorage, moduleAction) }
			withParameters<NameKoinDi> { parametersOf(valueStorage, moduleAction) }
			withParameters<QpayStructureKoinDi> { parametersOf(valueStorage, moduleAction, moduleName) }
			withParameters<QpayCreateKoinDi> { parametersOf(valueStorage, moduleAction, moduleName, structure) }
		}
	}

	/** Если верхнеуровневая сущность UI сможет создаться, то для этого скоупа всё ок.
	 * Этот тест более честный по скоупам, т.к. не проверяет scoped-сущности вне самого скоупа, как это делает checkModules */
	@TestFactory
	fun verifyEachScope(): Collection<DynamicTest> {
		val koinApp = makeKoinDi()

		return listOf(
			stepScopeDynamicTest({ koinApp.koin.getSelectionKoinDi(valueStorage) }, { it.getUi() }),
			stepScopeDynamicTest({ koinApp.koin.getQpayNameKoinDi(valueStorage, moduleAction) }, { it.getUi() }),
			stepScopeDynamicTest({ koinApp.koin.getNameKoinDi(valueStorage, moduleAction) }, { it.getUi() }),
			stepScopeDynamicTest({ koinApp.koin.getStructureKoinDi(valueStorage, moduleAction, moduleName) }, { it.getUi() }),
			stepScopeDynamicTest({ koinApp.koin.getCreateKoinDi(valueStorage, moduleAction, moduleName, structure) }, { it.getUi() }),
		)
	}

	private fun makeKoinDi() = makeDi(context).apply {
		koin.declare(ideScriptEngineManager)
		koin.declare(moduleActionService)
	}

	private fun <T : Any> stepScopeDynamicTest(createScope: () -> T, scopeAction: (T) -> Unit): DynamicTest {
		val scope = createScope()
		val testName = scope::class.simpleName!!
		return DynamicTest.dynamicTest(testName) {
			assertNotNull(scopeAction(scope))
		}
	}
}