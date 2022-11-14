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
import dev.itssho.module.service.action.module.ModuleActionService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.junit5.mock.MockProviderExtension
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DiTest : KoinTest {

	@RegisterExtension
	val mockProvider = MockProviderExtension.create { clazz ->
		// Setup your nock framework
		Mockito.mock(clazz.java)
	}

	@Test
	fun verifyKoinApp() {
		// TODO Эти типы задаются в конкретных модулях (в scope). Подумать, нужно ли создавать модули ровно при входе в шаг?
		//  Тогда для каждого модуля будет свой конструктор. Тогда моки ho, valuestorage не будут глобальными.
		val ho: HierarchyObject = mock()
		val valueStorage: FullyEditableValueStorage = mock()
		val moduleAction: ModuleAction = mock()

		val ideScriptEngineManager: IdeScriptEngineManager = mock()
		val moduleActionService: ModuleActionService = mock()

		val context: JBContext = mock()
		val project: Project = mock()
		val rootDirectory: PsiDirectory = mock()
		val rootDirectoryVirtualFile: VirtualFile = mock()

		whenever(context.ideProject).thenReturn(project)

		whenever(context.mainFolderPsiDirectory).thenReturn(rootDirectory)
		whenever(rootDirectory.virtualFile).thenReturn(rootDirectoryVirtualFile)
		whenever(rootDirectoryVirtualFile.path).thenReturn("abvgd")


		makeDi(context).apply {
			koin.declare(ho)
			koin.declare(valueStorage, secondaryTypes = listOf(ValueStorage::class, MutableValueStorage::class))
			koin.declare(moduleAction)
			koin.declare(ideScriptEngineManager)
			koin.declare(moduleActionService)
			checkModules()
		}
	}

	fun separateCheck() {
		// TODO попробовать раздельные проверки по каждому степу
	}
}