package di

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.qpay.module.actor.di.makeDi
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
		val ho: HierarchyObject = mock()

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
			checkModules()
		}
	}
}