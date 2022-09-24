package ui

import com.intellij.openapi.project.Project
import dev.itssho.module.core.actor.JBContext
import dev.itssho.module.hierarchy.HierarchyObject
import dev.itssho.module.qpay.module.actor.di.makeDi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.environmentProperties
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declare
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
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

		val context: JBContext = mock {
			on { ideProject } doReturn mock<Project>()
		}

		makeDi(context).apply {
			koin.declare(ho)
			checkModules()
		}
	}
}